plugins {
    java
    `maven-publish`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("commons-net:commons-net:3.9.0")
    compileOnly("com.rabbitmq:amqp-client:5.18.0")
    testImplementation("net.jqwik:jqwik:1.3.0")
    testImplementation("junit:junit:4.6")
}

group = "com.extollit"
version = "2.18.1"
description = "RabbitMQ Manager"
java.sourceCompatibility = JavaVersion.VERSION_1_8

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}
