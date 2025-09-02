package com.example.vision.presentation.screens.employee

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vision.data.model.*
import com.example.vision.ui.components.StandardButton
import com.example.vision.ui.components.VisionTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEmployeeScreen(
    onNavigateBack: () -> Unit,
    onCreateEmployee: (User) -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var selectedPermissions by remember { mutableStateOf(setOf<Permission>()) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Employee") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Text(
                text = "Create New Employee Account",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = "Fill in the details below to create a new employee account",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            // Personal Information Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Personal Information",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        VisionTextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            label = "First Name",
                            leadingIcon = Icons.Default.Person,
                            modifier = Modifier.weight(1f)
                        )
                        
                        VisionTextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            label = "Last Name",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    
                    VisionTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email Address",
                        leadingIcon = Icons.Default.Email
                    )
                    
                    VisionTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = "Username",
                        leadingIcon = Icons.Default.AccountCircle
                    )
                    
                    VisionTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        leadingIcon = Icons.Default.Lock,
                        visualTransformation = PasswordVisualTransformation()
                    )
                }
            }
            
            // Employment Information Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Employment Information",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    VisionTextField(
                        value = department,
                        onValueChange = { department = it },
                        label = "Department",
                        leadingIcon = Icons.Default.Business
                    )
                    
                    VisionTextField(
                        value = position,
                        onValueChange = { position = it },
                        label = "Position/Title",
                        leadingIcon = Icons.Default.WorkspacePremium
                    )
                }
            }
            
            // Permissions Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Permissions",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Permission.values().forEach { permission ->
                        PermissionCheckItem(
                            permission = permission,
                            isSelected = selectedPermissions.contains(permission),
                            onToggle = { isSelected ->
                                selectedPermissions = if (isSelected) {
                                    selectedPermissions + permission
                                } else {
                                    selectedPermissions - permission
                                }
                            }
                        )
                    }
                }
            }
            
            // Error Message
            if (showError) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = errorMessage,
                        modifier = Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
            
            // Create Button
            StandardButton(
                text = "Create Employee Account",
                onClick = {
                    if (validateForm(firstName, lastName, email, username, password, department, position)) {
                        val newEmployee = User(
                            id = "emp_${System.currentTimeMillis()}",
                            email = email,
                            username = username,
                            role = UserRole.EMPLOYEE,
                            profile = UserProfile(
                                firstName = firstName,
                                lastName = lastName,
                                displayName = "$firstName $lastName",
                                bio = "Employee at $department"
                            ),
                            employeeData = EmployeeData(
                                employeeId = "EMP${System.currentTimeMillis()}",
                                department = department,
                                position = position,
                                permissions = selectedPermissions.toList()
                            )
                        )
                        onCreateEmployee(newEmployee)
                    } else {
                        showError = true
                        errorMessage = "Please fill in all required fields"
                    }
                },
                enabled = firstName.isNotBlank() && lastName.isNotBlank() && 
                         email.isNotBlank() && username.isNotBlank() && 
                         password.isNotBlank() && department.isNotBlank() && position.isNotBlank(),
                icon = Icons.Default.PersonAdd
            )
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun PermissionCheckItem(
    permission: Permission,
    isSelected: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle(!isSelected) }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = onToggle,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary
            )
        )
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = permission.getDisplayName(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = permission.getDescription(),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun Permission.getDisplayName(): String = when (this) {
    Permission.VIEW_CUSTOMER_PROFILES -> "View Customer Profiles"
    Permission.EDIT_CUSTOMER_PROFILES -> "Edit Customer Profiles"
    Permission.VIEW_EYE_PHOTOS -> "View Eye Photos"
    Permission.UPLOAD_EYE_PHOTOS -> "Upload Eye Photos"
    Permission.MANAGE_APPOINTMENTS -> "Manage Appointments"
    Permission.GENERATE_REPORTS -> "Generate Reports"
}

private fun Permission.getDescription(): String = when (this) {
    Permission.VIEW_CUSTOMER_PROFILES -> "Access customer information and eye data"
    Permission.EDIT_CUSTOMER_PROFILES -> "Modify customer profiles and eye records"
    Permission.VIEW_EYE_PHOTOS -> "View customer eye photographs"
    Permission.UPLOAD_EYE_PHOTOS -> "Upload and manage eye photos"
    Permission.MANAGE_APPOINTMENTS -> "Create and manage customer appointments"
    Permission.GENERATE_REPORTS -> "Generate analytics and reports"
}

private fun validateForm(
    firstName: String,
    lastName: String,
    email: String,
    username: String,
    password: String,
    department: String,
    position: String
): Boolean {
    return firstName.isNotBlank() && 
           lastName.isNotBlank() && 
           email.isNotBlank() && 
           email.contains("@") &&
           username.isNotBlank() && 
           password.isNotBlank() && 
           password.length >= 6 &&
           department.isNotBlank() && 
           position.isNotBlank()
}