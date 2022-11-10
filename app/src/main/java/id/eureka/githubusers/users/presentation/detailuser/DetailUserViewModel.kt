package id.eureka.githubusers.users.presentation.detailuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.eureka.githubusers.core.model.Result
import id.eureka.githubusers.users.domain.usecase.GetRepositoriesUseCase
import id.eureka.githubusers.users.domain.usecase.GetUserByUserIdOrUserNameUseCase
import id.eureka.githubusers.users.presentation.model.DetailUserUIState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(
    private val getUserByUserIdOrUserNameUseCase: GetUserByUserIdOrUserNameUseCase,
    private val getRepositoriesUseCase: GetRepositoriesUseCase,
) : ViewModel() {

    private val _detailUserState: MutableStateFlow<DetailUserUIState> =
        MutableStateFlow(DetailUserUIState.Empty)
    val detailUserState = _detailUserState.asStateFlow()

    fun getUserDetail(userName: String, userId: Int) {
        viewModelScope.launch {
            getUserByUserIdOrUserNameUseCase.getUserByUserIdOrUserName(userName, userId)
                .collectLatest { result ->
                    when (result) {
                        is Result.Error -> _detailUserState.value =
                            DetailUserUIState.Error(result.message)
                        is Result.Success -> _detailUserState.value =
                            DetailUserUIState.GetUserDetailSuccess(result.data)
                    }
                }
        }
    }

    fun getUserRepositories(userName: String, userId: Int) {
        viewModelScope.launch {

            getRepositoriesUseCase.getRepositories(userName, userId)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collectLatest { result ->
                    _detailUserState.value = DetailUserUIState.GetUserRepositoriesSuccess(result)
                }
        }
    }
}