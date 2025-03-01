import com.sercroft.dummyappasd.data.dao.UserDao
import com.sercroft.dummyappasd.data.model.Post
import com.sercroft.dummyappasd.data.model.User
import com.sercroft.dummyappasd.data.repository.PostRepository
import com.sercroft.dummyappasd.data.service.JsonPlaceholderApiService
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class PostRepositoryTest {

    private val apiService: JsonPlaceholderApiService = mock(JsonPlaceholderApiService::class.java)
    private val userDao: UserDao = mock(UserDao::class.java)
    private val repository = PostRepository(apiService, userDao)

    @Test
    fun getPosts_shouldReturnPostsFromAPI() = runBlocking {
        val mockPosts = listOf(Post(1, "John Doe", "johndoe", "john@example.com", "123-456-7890", "johndoe.com"))
        `when`(apiService.getPosts()).thenReturn(Response.success(mockPosts))

        val result = repository.getPosts()
        assertEquals(mockPosts, result)
    }

    @Test(expected = Exception::class)
    fun getPosts_shouldThrowExceptionWhenAPIFails() = runBlocking<Unit> {
        `when`(apiService.getPosts()).thenReturn(Response.error(500, ResponseBody.create(null, "Error")))

        repository.getPosts()
    }

    @Test
    fun insertUsers_shouldCallUserDaoInsertAll() = runBlocking {
        val users = listOf(User(1, "John Doe", "johndoe", "john@example.com", "123-456-7890", "johndoe.com"))

        repository.insertUsers(users)
        verify(userDao, times(1)).insertAll(users)
    }

    @Test
    fun getAllUsers_shouldReturnUsersFromUserDao() = runBlocking {
        val mockUsers = listOf(User(1, "John Doe", "johndoe", "john@example.com", "123-456-7890", "johndoe.com"))
        `when`(userDao.getAllUsers()).thenReturn(mockUsers)

        val result = repository.getAllUsers()
        assertEquals(mockUsers, result)
    }
}