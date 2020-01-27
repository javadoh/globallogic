package com.globallogic.homelist.data.repository

import com.globallogic.homelist.HomeListTestData
import com.globallogic.homelist.data.mapper.HomeListDomainDataMapper
import com.globallogic.homelist.domain.repository.IHomeListRepo
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
class HomeListRepositoryImplTest {

        private lateinit var underTest: IHomeListRepo

        private val feedDomainDataMapper =
            HomeListDomainDataMapper()

        @Mock
        private lateinit var localDataSource: IHomeListLocalDataSource

        @Mock
        private lateinit var remoteDataSource: IHomeListRemoteDataSource

        @Before
        fun setUp() {
            MockitoAnnotations.initMocks(this)

            underTest = HomeListRepoImpl(
                feedDomainDataMapper,
                localDataSource,
                remoteDataSource
            )
        }

        @After
        fun tearDown() {
            Mockito.verifyNoMoreInteractions(localDataSource, remoteDataSource)
        }

        @Test
        fun `getFeed() remote call succeeds, chains local call and saves local data`() {
            val feedArticleData = HomeListTestData.getFeedArticleData()
            val feedArticleDomain = feedArticleData.map { feedDomainDataMapper.mapFrom(it) }

            BDDMockito.given(remoteDataSource.getHomeList()).willReturn(Observable.just(feedArticleData))
            BDDMockito.given(localDataSource.getHomeList()).willReturn(Observable.just(feedArticleData))

            underTest.getHomeList().test()
                .assertSubscribed()
                .assertValueCount(2)
                .assertValues(feedArticleDomain, feedArticleDomain)
                .assertComplete()

            Mockito.verify(localDataSource, Mockito.times(1)).saveHomeListItem(feedArticleData)
            Mockito.verify(localDataSource, Mockito.times(1)).getHomeList()
            Mockito.verify(remoteDataSource, Mockito.times(1)).getHomeList()
        }

        @Test
        fun `getFeed() remote call fails, local call succeeds`() {
            val feedArticleData = HomeListTestData.getFeedArticleData()
            val feedArticleDomain = feedArticleData.map { feedDomainDataMapper.mapFrom(it) }

            val throwable = Throwable("Network call error!")
            BDDMockito.given(remoteDataSource.getHomeList()).willReturn(Observable.error(throwable))
            BDDMockito.given(localDataSource.getHomeList()).willReturn(Observable.just(feedArticleData))

            underTest.getHomeList()
                .test()
                .assertSubscribed()
                .assertValueCount(1)
                .assertValues(feedArticleDomain)
                .assertComplete()

            Mockito.verify(localDataSource, Mockito.times(1)).getHomeList()
            Mockito.verify(remoteDataSource, Mockito.times(1)).getHomeList()
        }

        @Test
        fun `getFeed() remote call succeeds, local call fails`() {
            val feedArticleData = HomeListTestData.getFeedArticleData()
            val feedArticleDomain = feedArticleData.map { feedDomainDataMapper.mapFrom(it) }

            BDDMockito.given(remoteDataSource.getHomeList()).willReturn(Observable.just(feedArticleData))
            val throwable = Throwable("Local call error!")
            BDDMockito.given(localDataSource.getHomeList()).willReturn(Observable.error(throwable))

            underTest.getHomeList()
                .test()
                .assertSubscribed()
                .assertValueCount(1)
                .assertValues(feedArticleDomain)
                .assertComplete()

            Mockito.verify(localDataSource, Mockito.times(1)).saveHomeListItem(feedArticleData)
            Mockito.verify(localDataSource, Mockito.times(1)).getHomeList()
            Mockito.verify(remoteDataSource, Mockito.times(1)).getHomeList()
        }

        @Test
        fun `getFeed() calls fail`() {
            val throwable1 = Throwable("Network call error!")
            BDDMockito.given(remoteDataSource.getHomeList()).willReturn(Observable.error(throwable1))
            val throwable2 = Throwable("Local call error!")
            BDDMockito.given(localDataSource.getHomeList()).willReturn(Observable.error(throwable2))

            underTest.getHomeList()
                .test()
                .assertSubscribed()
                .assertComplete()

            Mockito.verify(localDataSource, Mockito.times(1)).getHomeList()
            Mockito.verify(remoteDataSource, Mockito.times(1)).getHomeList()
        }
    }