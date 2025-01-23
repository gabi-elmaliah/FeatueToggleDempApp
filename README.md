# **Feature Toggle Library**

A lightweight and flexible library to manage feature toggles in Android applications. Easily enable, disable, and manage features dynamically using a centralized API.

---

## **Features**
- Create, update, and delete feature toggles.
- Fetch active toggles and filter by date or range.
- View feature statistics (total and active toggles).
- Designed for seamless integration into Android applications.
- Includes a demo app showcasing library functionality.

---

### **Usage**

#### Examples of Usage

#### **Initialize the Library**
```java
FeatureToggle featureToggle = new FeatureToggle();
```

#### **2. Fetch Active Features**
Retrieve all active feature toggles for the current package:

```java
FeatureToggle.getActiveFeatures(context, new FeatureToggle.Callback_Data<List<FeatureToggleItem>>() {
    @Override
    public void onSuccess(List<FeatureToggleItem> activeFeatures) {
        for (FeatureToggleItem feature : activeFeatures) {
            Log.d("FeatureToggle", "Active Feature: " + feature.getName());
        }
    }

    @Override
    public void onError(String errorMessage) {
        Log.e("FeatureToggle", "Error fetching active features: " + errorMessage);
    }
});
```

#### **3. Create a Feature Toggle**
Add a new feature toggle to the database:

```java
FeatureToggleItem newFeature = new FeatureToggleItem();
newFeature.setName("christmas_theme");
newFeature.setDescription("Enable Christmas theme with red and green colors.");
newFeature.setBeginning_date("2025-12-01 00:00:00");
newFeature.setExpiration_date("2025-12-31 23:59:59");

FeatureToggle.createFeatureToggle(context, newFeature, new FeatureToggle.Callback_Data<String>() {
    @Override
    public void onSuccess(String message) {
        Log.d("FeatureToggle", "Feature created successfully: " + message);
    }

    @Override
    public void onError(String errorMessage) {
        Log.e("FeatureToggle", "Error creating feature toggle: " + errorMessage);
    }
});
```

#### **4. Update Feature Dates**
Modify the beginning and expiration dates for an existing feature:

```java
FeatureToggleItem updatedFeature = new FeatureToggleItem();
updatedFeature.setBeginning_date("2025-12-15 00:00:00");
updatedFeature.setExpiration_date("2025-12-31 23:59:59");

FeatureToggle.updateFeatureDates(context, "feature-id", updatedFeature, new FeatureToggle.Callback_Data<String>() {
    @Override
    public void onSuccess(String message) {
        Log.d("FeatureToggle", "Feature dates updated: " + message);
    }

    @Override
    public void onError(String errorMessage) {
        Log.e("FeatureToggle", "Error updating feature dates: " + errorMessage);
    }
});
```

#### **5. Delete a Feature Toggle**
Remove a feature toggle by ID:

```java
FeatureToggle.deleteFeatureToggle(context, "feature-id", new FeatureToggle.Callback_Data<String>() {
    @Override
    public void onSuccess(String message) {
        Log.d("FeatureToggle", "Feature deleted successfully: " + message);
    }

    @Override
    public void onError(String errorMessage) {
        Log.e("FeatureToggle", "Error deleting feature: " + errorMessage);
    }
});
```

---

## **How to Use**

To use this library in your project, follow these steps:

1. **Add JitPack to your project-level `build.gradle` or `settings.gradle`:**

   ```kotlin
   repositories {
       maven { url = uri("https://jitpack.io") }
   }
   ```

2. **Add the library dependency to your app-level `build.gradle`:**

   ```kotlin
   dependencies {
       implementation("com.github.gabi-elmaliah:FeatureToggleLibrary:1.0.0")
   }
   ```

3. **Using Gradle with `libs.versions.toml`:**
    - Add the following entry to `libs.versions.toml`:
      ```toml
      [libraries]
      feature-toggle-library = { module = "com.github.gabi-elmaliah:FeatureToggleLibrary", version = "1.0.0" }
      ```
    - Reference it in your `build.gradle`:
      ```kotlin
      dependencies {
          implementation(libs.feature-toggle-library)
      }
      ```

---

## License

This library is licensed under the MIT License. See the [MIT LICENSE](License) file for details.
