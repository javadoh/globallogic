package com.globallogic.homelist.ui

import com.globallogic.homelist.HomeListTestData
import com.globallogic.homelist.presentation.model.HomeList
import com.globallogic.homelist.presentation.model.resource.Resource
import com.globallogic.homelist.presentation.model.resource.Status
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class HomeListResourceStatusDelegateTest {
    @Mock
    private lateinit var feedView: IHomeListView

    private lateinit var underTest: HomeListResourceStatusDelegate

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        underTest = HomeListResourceStatusDelegate(feedView)
    }

    @Test
    fun handlesSuccess() {
        val data = HomeListTestData.feed()
        val testResource = Resource<List<HomeList?>>(Status.SUCCESS, data, null)

        underTest.handleStatus(testResource)

        Mockito.verify(feedView).showLoading(false)
        Mockito.verify(feedView).showError(false)
        Mockito.verify(feedView).showList(true, testResource.data)
    }

    @Test
    fun handlesError() {
        val errorMessage = "Error happened"
        val testResource = Resource<List<HomeList?>>(Status.ERROR, null, errorMessage)

        underTest.handleStatus(testResource)

        Mockito.verify(feedView).showLoading(false)
        Mockito.verify(feedView).showList(false)
        Mockito.verify(feedView).showError(true, testResource.message)
    }

    @Test
    fun handlesLoading() {
        val testResource = Resource<List<HomeList?>>(Status.LOADING, null, null)

        underTest.handleStatus(testResource)

        Mockito.verify(feedView).showError(false)
        Mockito.verify(feedView).showList(false)
        Mockito.verify(feedView).showLoading(true)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(feedView)
    }
}