package com.globallogic.homelist.local.source

import com.globallogic.homelist.HomeListTestData
import com.globallogic.homelist.data.repository.IHomeListLocalDataSource
import com.globallogic.homelist.local.database.HomeListDAO
import com.globallogic.homelist.local.mapper.HomeListDataLocalMapper
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class HomeLocalDataSrcImplTest {
        private lateinit var underTest: IHomeListLocalDataSource

        @Mock
        private lateinit var feedDAO: HomeListDAO

        private val feedDataLocalMapper = HomeListDataLocalMapper()

        @Before
        fun setUp() {
            MockitoAnnotations.initMocks(this)

            underTest = HomeListLocalDataSourceImpl(
                feedDataLocalMapper,
                feedDAO
            )
        }

        @Test
        fun getFeed_success() {
            val localFeed = HomeListTestData.getLocalFeed()
            BDDMockito.given(feedDAO.getHomeList()).willReturn(Observable.just(localFeed))

            underTest.getHomeList()
                .test()
                .assertSubscribed()
                .assertValue { feed ->
                    localFeed.containsAll(
                        feed.map { feedDataLocalMapper.mapTo(it) }
                    )
                }

            Mockito.verify(feedDAO).getHomeList()
        }

        @Test
        fun getFeed_error() {
            val throwable = Throwable("Error!")
            BDDMockito.given(feedDAO.getHomeList()).willReturn(Observable.error(throwable))

            underTest.getHomeList()
                .test()
                .assertSubscribed()
                .assertError(throwable)

            Mockito.verify(feedDAO).getHomeList()
        }
}