package com.example.vision.data.repository

import com.example.vision.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomerRepository @Inject constructor() {
    
    private val _customers = MutableStateFlow<List<User>>(generateMockCustomers())
    val customers: Flow<List<User>> = _customers.asStateFlow()
    
    fun getCustomerById(customerId: String): User? {
        return _customers.value.find { it.id == customerId }
    }
    
    fun searchCustomers(query: String): List<User> {
        return if (query.isBlank()) {
            _customers.value
        } else {
            _customers.value.filter { customer ->
                customer.profile.displayName.contains(query, ignoreCase = true) ||
                customer.email.contains(query, ignoreCase = true) ||
                customer.id.contains(query, ignoreCase = true)
            }
        }
    }
    
    fun updateCustomer(customer: User) {
        val currentList = _customers.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == customer.id }
        if (index != -1) {
            currentList[index] = customer
            _customers.value = currentList
        }
    }
    
    fun addCustomer(customer: User) {
        val currentList = _customers.value.toMutableList()
        currentList.add(customer)
        _customers.value = currentList
    }
    
    fun addEmployee(employee: User) {
        val currentList = _customers.value.toMutableList()
        currentList.add(employee)
        _customers.value = currentList
    }
    
    fun getEmployees(): List<User> {
        return _customers.value.filter { it.role == UserRole.EMPLOYEE }
    }
    
    private fun generateMockCustomers(): List<User> {
        return listOf(
            User(
                id = "cust_001",
                email = "sarah.johnson@email.com",
                isActive = true,
                role = UserRole.CUSTOMER,
                profile = UserProfile(
                    firstName = "Sarah",
                    lastName = "Johnson",
                    displayName = "Sarah Johnson",
                    bio = "Regular customer",
                    phoneNumber = "+1 (555) 123-4567",
                    gender = Gender.FEMALE,
                    location = Location(
                        city = "Springfield",
                        state = "IL",
                        zipCode = "62701"
                    ),
                    avatarUrl = null,
                    eyeData = listOf(
                        EyeData(
                            id = "eye_001",
                            patientId = "cust_001",
                            eyeSide = EyeSide.LEFT,
                            irisColor = IrisColor(
                                primaryColor = "Brown",
                                secondaryColor = "Amber",
                                pattern = IrisPattern.RADIAL,
                                colorCode = "#8B4513"
                            ),
                            pupilSize = 3.5f,
                            scleraShade = "White",
                            limbusDefinition = "Well-defined",
                            specialCharacteristics = listOf("Central heterochromia"),
                            measurements = EyeMeasurements(
                                horizontalDiameter = 24.5f,
                                verticalDiameter = 24.3f,
                                irisSize = 11.6f,
                                depthMeasurement = 2.1f
                            ),
                            photos = listOf(
                                EyePhoto(
                                    id = "photo_001",
                                    uri = "",
                                    type = PhotoType.NATURAL_EYE,
                                    dateTaken = LocalDate.of(2024, 8, 20),
                                    notes = "Standard front view capture"
                                )
                            )
                        ),
                        EyeData(
                            id = "eye_002",
                            patientId = "cust_001",
                            eyeSide = EyeSide.RIGHT,
                            irisColor = IrisColor(
                                primaryColor = "Brown",
                                secondaryColor = "Amber",
                                pattern = IrisPattern.RADIAL,
                                colorCode = "#8B4513"
                            ),
                            pupilSize = 3.4f,
                            scleraShade = "White",
                            limbusDefinition = "Well-defined",
                            specialCharacteristics = listOf("Matches left eye closely"),
                            measurements = EyeMeasurements(
                                horizontalDiameter = 24.3f,
                                verticalDiameter = 24.1f,
                                irisSize = 11.5f,
                                depthMeasurement = 2.0f
                            ),
                            photos = listOf(
                                EyePhoto(
                                    id = "photo_002",
                                    uri = "",
                                    type = PhotoType.NATURAL_EYE,
                                    dateTaken = LocalDate.of(2024, 8, 20),
                                    notes = "Standard front view capture"
                                )
                            )
                        )
                    )
                ),
                employeeData = null
            ),
            User(
                id = "cust_002",
                email = "michael.chen@email.com",
                isActive = true,
                role = UserRole.CUSTOMER,
                profile = UserProfile(
                    firstName = "Michael",
                    lastName = "Chen",
                    displayName = "Michael Chen",
                    bio = "Young professional",
                    phoneNumber = "+1 (555) 234-5678",
                    gender = Gender.MALE,
                    location = Location(
                        city = "Chicago",
                        state = "IL",
                        zipCode = "60601"
                    ),
                    avatarUrl = null,
                    eyeData = listOf(
                        EyeData(
                            id = "eye_003",
                            patientId = "cust_002",
                            eyeSide = EyeSide.LEFT,
                            irisColor = IrisColor(
                                primaryColor = "Blue",
                                secondaryColor = "Grey",
                                pattern = IrisPattern.CRYPTS,
                                colorCode = "#4169E1"
                            ),
                            pupilSize = 4.0f,
                            scleraShade = "Bright White",
                            limbusDefinition = "Moderate",
                            specialCharacteristics = listOf("Beautiful blue iris", "Visible crypts"),
                            measurements = EyeMeasurements(
                                horizontalDiameter = 25.0f,
                                verticalDiameter = 24.8f,
                                irisSize = 12.0f,
                                depthMeasurement = 2.3f
                            ),
                            photos = listOf(
                                EyePhoto(
                                    id = "photo_003",
                                    uri = "",
                                    type = PhotoType.NATURAL_EYE,
                                    dateTaken = LocalDate.of(2024, 8, 25),
                                    notes = "High quality capture"
                                )
                            )
                        )
                    )
                ),
                employeeData = null
            ),
            User(
                id = "cust_003",
                email = "emma.rodriguez@email.com",
                isActive = true,
                role = UserRole.CUSTOMER,
                profile = UserProfile(
                    firstName = "Emma",
                    lastName = "Rodriguez",
                    displayName = "Emma Rodriguez",
                    bio = "Experienced customer",
                    phoneNumber = "+1 (555) 345-6789",
                    gender = Gender.FEMALE,
                    location = Location(
                        city = "Austin",
                        state = "TX",
                        zipCode = "73301"
                    ),
                    avatarUrl = null,
                    eyeData = listOf(
                        EyeData(
                            id = "eye_004",
                            patientId = "cust_003",
                            eyeSide = EyeSide.LEFT,
                            irisColor = IrisColor(
                                primaryColor = "Green",
                                secondaryColor = "Gold",
                                pattern = IrisPattern.MIXED,
                                colorCode = "#228B22"
                            ),
                            pupilSize = 3.8f,
                            scleraShade = "Ivory",
                            limbusDefinition = "Well-defined",
                            specialCharacteristics = listOf("Unique green coloration", "Gold flecks"),
                            measurements = EyeMeasurements(
                                horizontalDiameter = 23.8f,
                                verticalDiameter = 23.6f,
                                irisSize = 11.2f,
                                depthMeasurement = 1.9f
                            ),
                            photos = listOf(
                                EyePhoto(
                                    id = "photo_004",
                                    uri = "",
                                    type = PhotoType.NATURAL_EYE,
                                    dateTaken = LocalDate.of(2024, 8, 30),
                                    notes = "Captured natural lighting"
                                ),
                                EyePhoto(
                                    id = "photo_005",
                                    uri = "",
                                    type = PhotoType.COLOR_MATCH,
                                    dateTaken = LocalDate.of(2024, 8, 30),
                                    notes = "Color matching reference"
                                )
                            )
                        ),
                        EyeData(
                            id = "eye_005",
                            patientId = "cust_003",
                            eyeSide = EyeSide.RIGHT,
                            irisColor = IrisColor(
                                primaryColor = "Green",
                                secondaryColor = "Gold",
                                pattern = IrisPattern.MIXED,
                                colorCode = "#228B22"
                            ),
                            pupilSize = 3.7f,
                            scleraShade = "Ivory",
                            limbusDefinition = "Well-defined",
                            specialCharacteristics = listOf("Symmetric to left eye"),
                            measurements = EyeMeasurements(
                                horizontalDiameter = 23.9f,
                                verticalDiameter = 23.7f,
                                irisSize = 11.3f,
                                depthMeasurement = 2.0f
                            ),
                            photos = listOf(
                                EyePhoto(
                                    id = "photo_006",
                                    uri = "",
                                    type = PhotoType.NATURAL_EYE,
                                    dateTaken = LocalDate.of(2024, 8, 30),
                                    notes = "Captured natural lighting"
                                )
                            )
                        )
                    )
                ),
                employeeData = null
            )
        )
    }
}