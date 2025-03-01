import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sercroft.dummyappasd.data.model.Post
import com.sercroft.dummyappasd.data.repository.PostRepository
import com.sercroft.dummyappasd.ui.viewmodel.PostViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PostViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: PostRepository = mock(PostRepository::class.java)
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: PostViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = PostViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchPosts_shouldUpdatePostsAndFilteredPosts() = runTest {
        val mockPosts = listOf(
            Post(
                1,
                "John Doe",
                "johndoe",
                "john@example.com",
                "123-456-7890",
                "johndoe.com"
            )
        )
        `when`(repository.getPosts()).thenReturn(mockPosts)

        val postsObserver: Observer<List<Post>> = mock(Observer::class.java) as Observer<List<Post>>
        val filteredPostsObserver: Observer<List<Post>> =
            mock(Observer::class.java) as Observer<List<Post>>
        val isLoadingObserver: Observer<Boolean> = mock(Observer::class.java) as Observer<Boolean>

        viewModel.posts.observeForever(postsObserver)
        viewModel.filteredPosts.observeForever(filteredPostsObserver)
        viewModel.isLoading.observeForever(isLoadingObserver)

        viewModel.fetchPosts()

        advanceUntilIdle()

        verify(postsObserver).onChanged(mockPosts)
        verify(filteredPostsObserver).onChanged(mockPosts)
        verify(isLoadingObserver, times(2)).onChanged(anyBoolean())
    }

    @Test
    fun filterPosts_shouldUpdateFilteredPostsWithMatchingPosts() {
        val mockPosts = listOf(
            Post(1, "John Doe", "johndoe", "john@example.com", "123-456-7890", "johndoe.com"),
            Post(2, "Jane Doe", "janedoe", "jane@example.com", "123-456-7890", "janedoe.com")
        )
        viewModel._posts.value = mockPosts

        val filteredPostsObserver: Observer<List<Post>> = mock(Observer::class.java) as Observer<List<Post>>
        viewModel.filteredPosts.observeForever(filteredPostsObserver)

        viewModel.filterPosts("Jane")

        val expectedFilteredPosts = listOf(
            Post(2, "Jane Doe", "janedoe", "jane@example.com", "123-456-7890", "janedoe.com")
        )
        verify(filteredPostsObserver).onChanged(expectedFilteredPosts)
    }
}