# Hyperswitch React + Java Integration

Build a simple checkout web-app to collect payment details and make a dummy payment. Included are some basic build and run scripts you can use to run the demo application.

## Introduction

This demo application uses the following tech-stack :

**Frontend :** `React` with `JavaScript`

**Backend :** `Java`  

## Prerequisites

Before running the demo app, please make sure to activate your Hyperswitch secret keys (API Key and Publishable Key) in your [Hyperswitch Dashboard](https://app.hyperswitch.io/developers). 

Don't have a Hyperswitch account? [Sign up here!](https://app.hyperswitch.io/register) 

## Running the sample

1. Add your keys :
    - Navigate to `src/App.jsx` and replace the placeholder `HYPERSWITCH_PUBLISHABLE_KEY` with your publishable key.
    - Navigate to `src/main/java/server.java` and replace the placeholder `HYPERSWITCH_API_KEY` with your API key.

2. Install the dependencies / build the server : 

~~~
npm install
~~~
~~~
mvn package
~~~

3. Run the server :

~~~
java -cp target/sample-jar-with-dependencies.jar server 
~~~

4. Run the client :

~~~
npm run start-client
~~~

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.
