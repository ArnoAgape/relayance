package com.kirabium.relayance.test

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/androidTest/assets/features"],
    glue = ["com.kirabium.relayance.steps"]
)
class RunCucumberTest