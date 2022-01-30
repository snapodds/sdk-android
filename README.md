## Documentation

For the full API documentation go to https://snapodds.github.io/sdk-android

## Support
In case of any questions or problems please contact us at [support@snapscreen.com](mailto:support@snapscreen.com).


## Requirements

Snapscreen requires at least **Android API Level 23 (Android 6.0)**.

Additionally, Snapscreen requires the Android NDK (Native Development Kit).

## Integrating Snapscreen

### Github Packages

#### Create a Personal Access Token on Github

* Inside your GitHub account:
* Settings -> Developer Settings -> Personal Access Tokens -> Generate new token
* Make sure you select the scope **read:packages** and Generate a token
* After generating make sure to copy your new personal access token. You cannot see it again! The only option is to generate a new key.

#### Add Github Packages Repository build.gradle inside the application module

Place a github.properties file containing your Github User ID and Personal Access Token in your project's root directory

```
gpr.user=GITHUB_USERID
gpr.key=PERSONAL_ACCESS_TOKEN
```

or alternative set the environment variables GPR_USER and GPR_API_KEY

Then define Github Packages as a repository in your application module's build.gradle by adding the following section.

```
def githubProperties = new Properties() githubProperties.load(new FileInputStream(rootProject.file("github.properties")))
repositories {
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/snapodds/sdk-android")
        
        credentials {        
            /** Create github.properties in root project folder file with     
            ** gpr.usr=GITHUB_USER_ID & gpr.key=PERSONAL_ACCESS_TOKEN 
            ** Or set env variable GPR_USER & GPR_API_KEY if not adding a properties file**/
            username = githubProperties['gpr.usr'] ?: System.getenv("GPR_USER")
            password = githubProperties['gpr.key'] ?: System.getenv("GPR_API_KEY")
        }

        metadataSources {
            mavenPom()
            google()
            mavenCentral()
            artifact()
        }
    }
}
```

**Note**: If you have configured your project repositories via settings.gradle and changed the dependencyResolutionManagement to maybe even FAIL_ON_PROJECT_REPOS you might need to change where you define the repository.

#### Add Dependency to build.gradle inside the application module

```
dependencies {
    //consume library
    implementation 'com.snapscreen.mobile:sdk-android'
}
```


## Using Snapscreen


### Obtain a client ID and secret

Contact Snapscreen to get a clientID and secret for your application.

### 3. Integrate Snapscreen SDK

In order to initialize Snapscreen, you need to call the following class method in your custom Application class in onCreate()

```
class MyApplication: Application() {
    
    override fun onCreate() {
        super.onCreate()

        Snapscreen.setup(this, "your-client-id", "your-client-secret", Environment.TEST)
    }
}
```

If you are provided with a custom backend URL by Snapscreen, you can use the following method to initalize the SDK:

```
Snapscreen.setup(this, "your-client-id", "your-client-secret", "custom-backend-url")
```

If preferred, you can additionally set the country and usState information based on your user's location:

Snapscreen.instance?.country = "US
Snapscreen.instance?.usState = "NJ"

Once you have initialized Snapscreen, you can also retrieve it by calling

```
Snapscreen.instance
```

### 4. Permissions

Snapscreen uses various Android permissions to function correctly. In detail the following permissions are necessary (and automatically defined by the Snapscreen SDK):

```
android.permission.INTERNET
Used for accessing the backend services of Snapscreen

android.permission.CAMERA
Used for offering snapping functionality and access to the camera

android.permission.VIBRATE
Used for device feedback during snapping functionality

android.permission.WRITE_EXTERNAL_STORAGE
Used for temporary image storage during processing
```

## Snapping Sports content

In order to snap a TV image and search for Sport event, you need to create and present an instance of **SnapFragment** via the newInstance class method. You can customize the snap settings by configuring the **SnapConfiguration** you pass to the initializer. The **SnapResultListener** will receive a callback once a sport event was successfully snapped.

It is recommended that you present the SnapFragment in it's own Activity.

It is also recommended to present and limit the Activity containing a SnapFragment be limited to portrait orientation.

```
SnapFragment.newInstance(SnapConfiguration(), this)
```

Once a sport event was successfully snapped, the following method is called in your SnapResultListener:

```
override fun snapFragmentDidSnapSportEvent(
        fragment: SnapFragment,
        sportEvent: SportEventSnapResultEntry
    ): Boolean {

    return true
}
```

The boolean return value indicates if you want the **SnapFragment** to immediately stop snapping. This is most likely the case since you want to present some follow-up UI for the found sport event.

Before you present the Snap UI, we recommend that you ask for the permission to use the camera with the following code snippet and only redirect the user to the Snap UI when the permission has been granted.

```
val isGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
if (isGranted) {
    startActivity(Intent(context, MySnapActivity::class.java))
} else {
    requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
}
```

## Fetching Odds for a sport event

In order to fetch odds for a sport event provided by Snapscreen in a consolidated form, you can use the following call to Snapscreen:

```
Snapscreen.instance?.getOddsForSportEvent(sportEventId) { snapOddsResult ->
}
```

The completion block is called either with a valid result or nil. See the documentation for the structure and content of the returned result.

Please note that for fetching odds via Snapscreen we highly recommend setting the **country** and **usState** fields on the snapscreen instance to receive accurate results.

## Customization - UI & Localization

If you want to customize any aspect of the **SnapFragment**, including displayed messages as well as colors, you can use the **UIConfiguration** object on the Snapscreen instance.

```
Snapscreen.instance?.uiConfiguration?.primaryColor = R.color.primary
```

Make sure to do all your desired customizations before presenting a SnapFragment.
You can also modify the configuration between presentations of the SnapFragment. Modifications while the fragment is present are not guaranteed to be respected.

## Using with a simulator or emulator

For snapping Snapscreen has dependencies to native libraries which are only available on actual devices. In order to still be able to develop the rest of your application and all features of SnapscreenKit not related to snapping, you need to add the following configuration in the android section of your application's build.gradle. The universalApk option set to true makes sure that when you are building your application, a universal APK is generated that contains all APK splits just like without the splits option.

```
splits {
    abi {
        enable true
        reset()
        include 'armeabi-v7a', 'mips', 'x86'
        universalApk true
    }
}
```

## Example

For a sample using the Snapscreen SDK, see the included Example project and reconfigure with your own client ID and secret before running.

## Support

In case of any questions or problems please contact us at [support@snapscreen.com](mailto:support@snapscreen.com).
