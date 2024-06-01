package com.babestudios.coroutines.part3.section9sharedflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class NewsViewModelFlow(
	newsRepository: NewsRepository
) : BaseViewModel() {
	private val _progressVisible = MutableStateFlow(false)
	val progressVisible = _progressVisible.asStateFlow()

	private val _errors = MutableSharedFlow<Throwable>()
	val errors = _errors.asSharedFlow()

	val newsToShow = newsRepository.fetchNews()
		.onStart { _progressVisible.emit(true) }
		.onCompletion { _progressVisible.emit(false) }
		.retry {
			it is ApiException
		}
		.catch {
			if (it !is ApiException)
				_errors.emit(it)
			println("Caught $it")
		}
		.shareIn(viewModelScope, SharingStarted.Eagerly)

}

class ApiException : Exception()

interface NewsRepository {
	fun fetchNews(): Flow<News>
}

abstract class BaseViewModel {
	protected val viewModelScope = CoroutineScope(
		Dispatchers.Main.immediate + SupervisorJob()
	)
}

class News(
	val title: String,
	val description: String,
	val imageUrl: String,
	val url: String
)

class FakeNewsRepository : NewsRepository {
	val newsList = List(100) { News("Title $it", "Description $it", "ImageUrl $it", "Url $it") }
	var fetchNewsStartDelay = 0L
	var fetchNewsDelay = 0L
	val failWith = mutableListOf<Throwable>()

	override fun fetchNews(): Flow<News> = flow {
		delay(fetchNewsStartDelay)
		failWith.removeFirstOrNull()?.let { throw it }
		newsList.forEach {
			delay(fetchNewsDelay)
			emit(it)
		}
	}
}

class NewsViewModelFlowTest {
	private lateinit var dispatcher: TestDispatcher
	private lateinit var newsRepository: FakeNewsRepository

	@BeforeEach
	fun setUp() {
		newsRepository = FakeNewsRepository()
		dispatcher = StandardTestDispatcher()
		Dispatchers.setMain(dispatcher)
	}

	@AfterEach
	fun tearDown() {
		Dispatchers.resetMain()
	}

	@Test
	fun `should show all news`() {
		val viewModel = NewsViewModelFlow(newsRepository)
		newsRepository.fetchNewsDelay = 1000
		val newsShown = mutableListOf<News>()
		viewModel.newsToShow.onEach {
			newsShown += it
		}.launchIn(CoroutineScope(dispatcher))
		dispatcher.scheduler.advanceUntilIdle()
		assertEquals(newsRepository.newsList, newsShown)
		assertEquals(newsRepository.newsList.size * newsRepository.fetchNewsDelay, dispatcher.scheduler.currentTime)
	}

	@Test
	fun `should show progress bar when loading news`() {
		val viewModel = NewsViewModelFlow(newsRepository)
		newsRepository.fetchNewsDelay = 1000
		val progressChanges = mutableListOf<Pair<Long, Boolean>>()
		viewModel.progressVisible.onEach {
			progressChanges += dispatcher.scheduler.currentTime to it
		}.launchIn(CoroutineScope(dispatcher))

		dispatcher.scheduler.advanceUntilIdle()
		assertEquals(
			listOf(0L to true, newsRepository.newsList.size * newsRepository.fetchNewsDelay to false),
			progressChanges
		)
	}

	@Test
	fun `should retry API exceptions`() {
		val exceptionsNum = 10
		newsRepository.failWith.addAll(List(exceptionsNum) { ApiException() })
		newsRepository.fetchNewsStartDelay = 1000
		val viewModel = NewsViewModelFlow(newsRepository)
		val errors = mutableListOf<Throwable>()
		viewModel.errors.onEach {
			errors += it
		}.launchIn(CoroutineScope(dispatcher))
		val newsShown = mutableListOf<News>()
		viewModel.newsToShow.onEach {
			newsShown += it
		}.launchIn(CoroutineScope(dispatcher))
		dispatcher.scheduler.advanceUntilIdle()
		assertEquals(0, errors.size)
		assertEquals(newsRepository.newsList, newsShown)
		assertEquals(newsRepository.fetchNewsStartDelay * (exceptionsNum + 1), dispatcher.scheduler.currentTime)
	}

	@Test
	fun `should catch exceptions`() {
		val exception = Exception()
		newsRepository.failWith.add(exception)
		newsRepository.fetchNewsStartDelay = 1000
		val viewModel = NewsViewModelFlow(newsRepository)
		val errors = mutableListOf<Throwable>()
		viewModel.errors.onEach {
			errors += it
		}.launchIn(CoroutineScope(dispatcher))
		dispatcher.scheduler.advanceUntilIdle()
		assertEquals(listOf(exception), errors)
		assertEquals(false, viewModel.progressVisible.value)
		assertEquals(1000, dispatcher.scheduler.currentTime)
	}
}
