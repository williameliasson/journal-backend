# Journal App

## What is it?
This is the backend of a project where you can log your mood over time and see a timeline and statistics. See also [the frontend](https://github.com/williameliasson/journal-frontend.git).

## How to run
1. Make sure Docker is running. [Get Docker](https://docs.docker.com/desktop/)
2. Set env variables by copying the provided `.env.example` file into `.env`, and type your variables.
3. In the root of the project, run `docker compose up --build`. This will start the required docker containers (the spring boot and the postgres containers).
4. The application should now be running at http://localhost:8080/

## Endpoints
All endpoints of the API begin with /api/.
### Auth
#### POST /register
Takes a json body of this structure:
```json
{
  "username" : "SOME_USERNAME",
  "password" : "SOME_PASSWORD",
  "matchingPassword" : "SOME_PASSWORD"
}
```
* Returns 201 CREATED if registration was successful. It also automatically logs in the user, by also providing a cookie `JSESSIONID`.
* Returns 409 CONFLICT if the username is already taken.
* Returns 400 BAD_REQUEST if the `password` and `matchingPassword` do not match.

#### POST /login
Takes a json body of this structure:
```json
{
  "username" : "SOME_USERNAME",
  "password" : "SOME_PASSWORD"
}
```
* Returns 200 OK if the credentials are correct. Also sends cookie `JSESSIONID`.
* Returns 401 UNAUTHORIZED if the credentials are incorrect, regardless of whether the username exists or not.

### Journal Entries

#### POST /me/journalentries
Takes a json body of this structure:
```json
{
  "title" : "Some text here",
  "moodId" : 1
}
```
* Returns 201 CREATED if the none of the fields are blank, the moodId exists, and the user is authenticated via the session cookie.
* Returns 403 FORBIDDEN if the user is not authenticated.
* Returns 400 BAD_REQUEST if any of the fields are blank, or the moodId does not exist, or the user is not authenticated.

#### GET /me/journalentries
Takes no body.
Returns a json list of the authenticated user's journal entries. The body has this structure:
```json
[
  {
    "id" : 1,
    "username" : "SOME_USERNAME",
    "title" : "Some text here",
    "createdAt" : "2026-09-20T15:43:00Z"
    "moodName" : "Happy",
    "moodIcon" : "😁"
  },
  {
    "id" : 2,
    "username" : "SOME_USERNAME",
    "title" : "Some other text here",
    "createdAt" : "2026-09-20T15:45:00Z"
    "moodName" : "Sad",
    "moodIcon" : "😢"
  }
]
```

* Returns 200 OK if the user is authenticated
* Returns 403 FORBIDDEN if the user is not authenticated.

### Moods

#### POST /moods
This endpoint is only meant to help the hosts of the application. It should be deactivated in a deployed environment.
Takes a json body of this structure:
```json
{
  "name" : "SOME MOOD NAME",
  "icon" : "🫪"
}
```
* Returns 201 CREATED if none of the fields are blank.
* Returns 400 BAD_REQUEST if any of the fields are blank.

#### GET /moods
Takes no body.
Returns a json list of all the moods. The list is of this structure:
```json
[
  {
    "id" : 1,
    "name" : "HAPPY",
    "icon" : "😁"
  },
  {
    "id" : 2,
    "name" : "SAD",
    "icon" : "😢"
  }
]
```
Returns 200 OK if the user is authenticated.
Returns 403 FORBIDDEN if the user is not authenticated.

### Statistics
#### GET /me/journalentries/statistics
Takes no body. Takes two url query params:
* `from` : A date in the format "YYYY-MM-DD".
* `to` : A date in the format "YYYY-MM-DD".

Returns a json body of this structure:
```json
{
    "username": "SOME_USERNAME",
    "moodStats": [
        {
            "moodName": "Calm",
            "percentage": 0.3333333333333333
        },
        {
            "moodName": "Excited",
            "percentage": 0.0
        },
        {
            "moodName": "Happy",
            "percentage": 0.16666666666666666
        },
        {
            "moodName": "Angry",
            "percentage": 0.0
        },
        {
            "moodName": "Sad",
            "percentage": 0.0
        },
        {
            "moodName": "Anxious",
            "percentage": 0.5
        },
        {
            "moodName": "Confused",
            "percentage": 0.0
        },
        {
            "moodName": "Tired",
            "percentage": 0.0
        }
    ],
    "journalEntryCount": 6
}
```
* Returns 200 OK if the user is authenticated. Does not matter if the user does not have any journal entries within the date range provided.
* Returns 403 FORBIDDEN if the user is not authenticated.


## Tech used
* Spring Boot
* Spring Security
* Postgres
* Docker / Docker compose
