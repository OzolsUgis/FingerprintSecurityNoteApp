<h1 align="center">Fingerprint Security Note App </h1>

<details open ="open">
  <summary>Contains</summary>
  <ol>
    <li>
      <a href='#about-the-project'>About Project</a>
        <ul>
          <li><a href="#built-with">Built With</a></li>
        </ul>
    </li>
 <li>
      <a href='#usage'>Usage</a>
        <ul>
          <li><a href="#code-samples">Code samples</a></li>
        </ul>
    </li> 
    <li>
      <a href='#contacts'>Contacts</a> 
    </li>
  </ol>
</details>


## About Project

Fingerprint security note app is a simple note application which allows you to save your notes under the highest security there is - biometrics, in this case fingerprint.
In this project highest emphasis is on implementing fingerprint reader. Saving notes in local database is realy basic. 
In this little project I tried to implement clean architecture 

### Built With

This application is built in  [Android Studio version 2020.3.1 (Artic Fox)](https://developer.android.com/studio?gclid=CjwKCAjwgb6IBhAREiwAgMYKRlU8WsxaTu6kg3JANeH6rEr8MrWyit5JaDfcTy0v1tTP0-DOmL1QnRoCxrcQAvD_BwE&gclsrc=aw.ds) 
using :

* [Kotlin](https://developer.android.com/kotlin)
* [Room](https://developer.android.com/jetpack/androidx/releases/room)
* [Jetpack Compose version 1.0.5](https://developer.android.com/jetpack/compose?gclid=EAIaIQobChMImIyxhI-i8gIVlgCiAx3kZgYlEAAYASAAEgL1J_D_BwE&gclsrc=aw.ds)
* [Courotines](https://developer.android.com/kotlin/coroutines?gclid=EAIaIQobChMIqZC4jo-i8gIVsAZ7Ch1rOASzEAAYASAAEgKAwvD_BwE&gclsrc=aw.ds)
* [DaggerHilt](https://developer.android.com/training/dependency-injection/hilt-android)


## Usage

### Code Samples

* This is fingerprint request launching function it only shows when there is biometric support on device

```kotlin
fun launchBiometricFingerprintReader(
    cancellationSignal: () -> CancellationSignal,
    authenticationSucceeded: () -> Unit,
    authenticationError: () -> Unit,
    context: Context
) {
    if (hasBiometricSupport(context)) {
        val biometricPrompt = BiometricPrompt.Builder(context)
            .setTitle(FINGERPRINT_DIALOG_TITLE)
            .setSubtitle(FINGERPRINT_DIALOG_SUBTITLE)
            .setConfirmationRequired(false)
            .setNegativeButton("Back", context.mainExecutor, { _, _ ->

            })
            .build()
        biometricPrompt.authenticate(
            cancellationSignal(),
            context.mainExecutor,
            authenticationCallback(authenticationSucceeded, authenticationError)
        )
    }
}

```

* In the next step i implemented fingerprint function in screens viewModel,
for success and error state i simply change the value of mutableStateOf Boolean

```kotlin 
    var hasFingerprintSecurityPassed by mutableStateOf(false)
    private set


    @RequiresApi(Build.VERSION_CODES.R)
    fun fingerprint() = viewModelScope.launch {
        launchBiometricFingerprintReader(
            { getCancelationSignal() },
            authenticationSucceeded = {
                hasFingerprintSecurityPassed = true
            },
            authenticationError = {
                hasFingerprintSecurityPassed = false
            },
            context
        )
    }

```

*  Last thing to do is implement this in UI. I simply did that with condition for the mutableStateOf Boolean 
if variable is true we show notes if false we show black screen 

```kotlin 
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel()
) {

    val securityPassed = viewModel.hasFingerprintSecurityPassed
    val notes = viewModel.allNotes.collectAsState(initial = emptyList()).value
    if (!securityPassed){
        viewModel.fingerprint()
        Box(modifier = Modifier.fillMaxSize().background(Color.Black))
    }else{
        FloatingAddButton(onButtonClick = { viewModel.onDialogIsOpenChange(true) }, viewModel)
        ListOfNotes(
            notes,
            viewModel,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}
```

 ## Contacts

   Ugis Ozols - ozols.ugis@outlook.com
     
   LinkedIn - www.linkedin.com/in/uģis-ozols-2192a8226

   Project Link - https://github.com/OzolsUgis/FingerprintSecurityNoteApp 





