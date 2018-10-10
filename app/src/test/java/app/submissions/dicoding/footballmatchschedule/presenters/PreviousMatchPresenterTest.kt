package app.submissions.dicoding.footballmatchschedule.presenters

import app.submissions.dicoding.footballmatchschedule.constants.League
import app.submissions.dicoding.footballmatchschedule.models.Events
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.PreviousMatchBehavior
import app.submissions.dicoding.footballmatchschedule.requests.to.LeagueSchedule
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class PreviousMatchPresenterTest {

  private lateinit var presenter: PreviousMatchPresenter

  @Mock
  private lateinit var behaviour: PreviousMatchBehavior

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    presenter = PreviousMatchPresenter(behaviour, Schedulers.trampoline(), Schedulers.trampoline())
  }

  @Test
  fun getData() {
    val testObserver: TestObserver<Events> = TestObserver()
    val observableResponse = LeagueSchedule.Request.get.past15(League.ENGLISH)

    observableResponse.subscribe(testObserver)

    testObserver.assertComplete()
    testObserver.assertNoErrors()

    presenter.getData()
    verify(behaviour).showLoading()
    observableResponse.subscribe {
      verify(behaviour).showData(PreviousMatchPresenter.mapEventsToMatchNewsHolder(it.events))
    }
    verify(behaviour).hideLoading()
  }
}