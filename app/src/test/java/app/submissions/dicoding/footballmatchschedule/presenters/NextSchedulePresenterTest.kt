package app.submissions.dicoding.footballmatchschedule.presenters

import app.submissions.dicoding.footballmatchschedule.constants.League
import app.submissions.dicoding.footballmatchschedule.models.Events
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.NextScheduleBehavior
import app.submissions.dicoding.footballmatchschedule.requests.to.LeagueSchedule
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class NextSchedulePresenterTest {

  @Mock
  private lateinit var behavior: NextScheduleBehavior

  private lateinit var presenter: NextSchedulePresenter

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    presenter = NextSchedulePresenter(behavior, Schedulers.trampoline(), Schedulers.trampoline())
  }

  @Test
  fun getData() {
    val testObserver: TestObserver<Events> = TestObserver()
    val observable = LeagueSchedule.Request.get.next15(League.ENGLISH)

    observable.subscribe(testObserver)

    testObserver.assertComplete()
    testObserver.assertNoErrors()

    presenter.getData()
    verify(behavior).showLoading()
    observable.subscribe {
      verify(behavior).showData(NextSchedulePresenter.getGroupedDataItem(it.events))
    }
    verify(behavior).hideLoading()


  }
}