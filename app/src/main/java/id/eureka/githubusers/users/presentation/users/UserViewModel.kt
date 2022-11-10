package id.eureka.githubusers.users.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import id.eureka.githubusers.R
import id.eureka.githubusers.core.provider.DispatcherProvider
import id.eureka.githubusers.core.provider.ResourceProvider
import id.eureka.githubusers.users.domain.usecase.SearchUserUseCase
import id.eureka.githubusers.users.presentation.model.UserUIState
import id.eureka.githubusers.users.presentation.model.mapper.UserDomainToUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class UserViewModel @Inject constructor(
    private val searchUserUseCase: SearchUserUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _userUiState: MutableStateFlow<UserUIState> = MutableStateFlow(UserUIState.Empty)
    val userUiState = _userUiState.asStateFlow()

    fun searchUser(userName: String) {
        viewModelScope.launch {
            flowOf(userName)
                .debounce(600)
                .onEach { _userUiState.value = UserUIState.Loading }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    searchUserUseCase.searchUser(query)
                }
                .flowOn(dispatcherProvider.getDefault())
                .catch { exception ->
                    _userUiState.value = UserUIState.Error(
                        exception.localizedMessage ?: resourceProvider.getString(
                            R.string.something_wrong
                        )
                    )
                }
                .collect { result ->
                    _userUiState.value =
                        UserUIState.SearchUserSuccess(result.map { UserDomainToUser.map(it) })
                }
        }
    }
}