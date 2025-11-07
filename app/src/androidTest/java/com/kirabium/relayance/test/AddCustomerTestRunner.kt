package com.kirabium.relayance.test

import io.cucumber.android.runner.CucumberAndroidJUnitRunner
import io.cucumber.junit.CucumberOptions

@CucumberOptions(
    features = ["features"],
    glue = ["com.kirabium.relayance.test"],
    plugin = ["pretty", "summary"]
)
class AddCustomerTestRunner : CucumberAndroidJUnitRunner()