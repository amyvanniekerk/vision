package com.example.vision.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vision.data.model.User
import com.example.vision.data.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CustomerState(
    val customers: List<User> = emptyList(),
    val selectedCustomer: User? = null,
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class CustomerEvent {
    data class SearchCustomers(val query: String) : CustomerEvent()
    data class SelectCustomer(val customerId: String) : CustomerEvent()
    data class UpdateCustomer(val customer: User) : CustomerEvent()
    data class AddCustomer(val customer: User) : CustomerEvent()
    data class AddEmployee(val employee: User) : CustomerEvent()
    object LoadCustomers : CustomerEvent()
    object ClearError : CustomerEvent()
}

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {
    
    private val _state = MutableStateFlow(CustomerState())
    val state: StateFlow<CustomerState> = _state.asStateFlow()
    
    init {
        loadCustomers()
    }
    
    fun handleEvent(event: CustomerEvent) {
        when (event) {
            is CustomerEvent.LoadCustomers -> loadCustomers()
            is CustomerEvent.SearchCustomers -> searchCustomers(event.query)
            is CustomerEvent.SelectCustomer -> selectCustomer(event.customerId)
            is CustomerEvent.UpdateCustomer -> updateCustomer(event.customer)
            is CustomerEvent.AddCustomer -> addCustomer(event.customer)
            is CustomerEvent.AddEmployee -> addEmployee(event.employee)
            is CustomerEvent.ClearError -> clearError()
        }
    }
    
    private fun loadCustomers() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                customerRepository.customers.collect { customers ->
                    _state.value = _state.value.copy(
                        customers = customers,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Failed to load customers: ${e.message}",
                    isLoading = false
                )
            }
        }
    }
    
    private fun searchCustomers(query: String) {
        _state.value = _state.value.copy(searchQuery = query, isLoading = true)
        try {
            val filteredCustomers = customerRepository.searchCustomers(query)
            _state.value = _state.value.copy(
                customers = filteredCustomers,
                isLoading = false
            )
        } catch (e: Exception) {
            _state.value = _state.value.copy(
                error = "Search failed: ${e.message}",
                isLoading = false
            )
        }
    }
    
    private fun selectCustomer(customerId: String) {
        try {
            val customer = customerRepository.getCustomerById(customerId)
            _state.value = _state.value.copy(selectedCustomer = customer)
        } catch (e: Exception) {
            _state.value = _state.value.copy(
                error = "Failed to load customer: ${e.message}"
            )
        }
    }
    
    private fun updateCustomer(customer: User) {
        try {
            customerRepository.updateCustomer(customer)
            _state.value = _state.value.copy(
                selectedCustomer = customer
            )
        } catch (e: Exception) {
            _state.value = _state.value.copy(
                error = "Failed to update customer: ${e.message}"
            )
        }
    }
    
    private fun addCustomer(customer: User) {
        try {
            customerRepository.addCustomer(customer)
        } catch (e: Exception) {
            _state.value = _state.value.copy(
                error = "Failed to add customer: ${e.message}"
            )
        }
    }
    
    private fun addEmployee(employee: User) {
        try {
            customerRepository.addEmployee(employee)
        } catch (e: Exception) {
            _state.value = _state.value.copy(
                error = "Failed to add employee: ${e.message}"
            )
        }
    }
    
    private fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}