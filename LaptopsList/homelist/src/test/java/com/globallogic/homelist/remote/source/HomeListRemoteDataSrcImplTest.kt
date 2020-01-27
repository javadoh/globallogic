package com.globallogic.homelist.remote.source

import android.util.Log
import com.globallogic.homelist.HomeListTestData
import com.globallogic.homelist.data.repository.IHomeListRemoteDataSource
import com.globallogic.homelist.remote.IHomeListService
import com.globallogic.homelist.remote.mapper.HomeListDataNetworkMapper
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class HomeListRemoteDataSrcImplTest {
        @Mock
        private lateinit var feedService: IHomeListService

        private val feedDataNetworkMapper =
            HomeListDataNetworkMapper()

        private lateinit var underTest : IHomeListRemoteDataSource

        @Before
        fun setUp() {
            MockitoAnnotations.initMocks(this)

            underTest = HomeListRemoteDataSrcImpl(feedDataNetworkMapper, feedService)
        }

        @After
        fun tearDown() {
            Mockito.verifyNoMoreInteractions(feedService)
        }

        @Test
        fun getFeed_success() {
            BDDMockito.given(feedService.getItems()).willReturn(
                Observable.just(
                    HomeListTestData.getArticleResponse(
                        arrayListOf(
                            HomeListTestData.getArticle()
                        )
                    )
                )
            )

            underTest.getHomeList()
                .test()
                .assertSubscribed()
                .assertValue { result ->
                    result == arrayListOf(feedDataNetworkMapper.mapFrom(HomeListTestData.getArticle()))


                }.assertComplete()

            Mockito.verify(feedService).getItems()
        }

        @Test
        fun getFeed_error() {
            val throwable = Throwable("Error!")
            BDDMockito.given(feedService.getItems()).willReturn(Observable.error(throwable))

            underTest.getHomeList()
                .test()
                .assertSubscribed()
                .assertError(throwable)

            Mockito.verify(feedService).getItems()
        }
}