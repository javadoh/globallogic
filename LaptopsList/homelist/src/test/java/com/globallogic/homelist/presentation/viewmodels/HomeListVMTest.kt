package com.globallogic.homelist.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globallogic.homelist.HomeListTestData
import com.globallogic.homelist.domain.entities.HomeListDataEntity
import com.globallogic.homelist.domain.usecases.GetHomeListItemUseCase
import com.globallogic.homelist.domain.usecases.base.ObservableUseCase
import com.globallogic.homelist.presentation.mapper.HomeListDomainItemsMapper
import com.globallogic.homelist.presentation.model.resource.Status
import com.globallogic.homelist.presentation.viewmodel.HomeListVM
import io.reactivex.Observable
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class HomeListVMTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var feedUseCase: ObservableUseCase<List<HomeListDataEntity?>, GetHomeListItemUseCase.Params>

    private lateinit var underTest: HomeListVM
    private val mapper = HomeListDomainItemsMapper()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        underTest = HomeListVM(feedUseCase, mapper)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(feedUseCase)
    }

    @Test
    fun `getFeed() succeeds`() {
        val params = GetHomeListItemUseCase.Params()
        BDDMockito.given(feedUseCase.buildUseCase(params))
            .willReturn(Observable.just(HomeListTestData.feedArticlesList()))

        val mappedResult = mapper.mapFrom(HomeListTestData.feedArticlesList())

        val feedResource = underTest.items

        feedResource.observeForever {}

        Assert.assertTrue(
            feedResource.value?.status == Status.SUCCESS
                    && feedResource.value?.data == mappedResult
        )

        Mockito.verify(feedUseCase).buildUseCase(params)
    }

    @Test
    fun `getFeed() fails`() {
        val errorMessage = "GetFeedUseCase error!"
        val throwable = Throwable(errorMessage)
        val params = GetHomeListItemUseCase.Params()
        BDDMockito.given(feedUseCase.buildUseCase(params))
            .willReturn(Observable.error(throwable))

        val feedResource = underTest.items

        feedResource.observeForever {}

        Assert.assertTrue(
            feedResource.value?.status == Status.ERROR
                    && feedResource.value?.message == errorMessage
        )

        Mockito.verify(feedUseCase).buildUseCase(params)
    }
}