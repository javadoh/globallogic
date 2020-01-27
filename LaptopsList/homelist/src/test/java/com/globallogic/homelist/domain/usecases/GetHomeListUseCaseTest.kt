package com.globallogic.homelist.domain.usecases

import com.globallogic.homelist.HomeListTestData
import com.globallogic.homelist.domain.repository.IHomeListRepo
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
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
class GetHomeListUseCaseTest {
        @Mock
        private lateinit var feedRepository: IHomeListRepo

        private lateinit var underTest: GetHomeListItemUseCase

        @Before
        fun setUp() {
            MockitoAnnotations.initMocks(this)

            underTest = GetHomeListItemUseCase(feedRepository, Schedulers.trampoline(), Schedulers.trampoline())
        }

        @After
        fun tearDown() {
            Mockito.verifyNoMoreInteractions(feedRepository)
        }

        @Test
        fun `getFeed() succeeds`() {
            BDDMockito.given(feedRepository.getHomeList()).willReturn(Observable.just(HomeListTestData.feedArticlesList()))

            underTest.buildUseCase(GetHomeListItemUseCase.Params())
                .test().assertSubscribed().assertValue {
                    it == HomeListTestData.feedArticlesList()
                }.assertComplete()

            Mockito.verify(feedRepository).getHomeList()
        }

        @Test
        fun `getFeed() fails`() {
            val throwable = Throwable("Error!")
            BDDMockito.given(feedRepository.getHomeList()).willReturn(Observable.error(throwable))

            underTest.buildUseCase(GetHomeListItemUseCase.Params())
                .test()
                .assertSubscribed()
                .assertError(throwable)

            Mockito.verify(feedRepository).getHomeList()
        }
}