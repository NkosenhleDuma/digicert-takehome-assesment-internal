# digicert-takehome-assesment-internal

Create a Spingboot application running within a docker container on port 8080 using Java17 and Jersey using an in-memory H2 database

Create an Agency Booking REST API.



Requirements
BACKEND:

The Booking API would be a hotel bookingEntity service that allows you to list, create, update and delete a reservation.
Basic error handing, logging.
Create a README detailing instructions on how to run and call the API.
Write appropriate unit and Integration tests
An agency might have several hundred bookings how would solve this issue through API

---

### Create Booking

`POST /api/bookings`

```json
{
    "num_adults": 1,
    "num_beds": 1,
    "check_in_date": "2024-04-18 20:49",
    "check_out_date": "2024-04-22 20:30",
    "customer_email": "test@email.com"
}
```


<br />

### Get Booking

`GET /api/bookings/:id`

<br />

### Get All Bookings

`GET /api/bookings/`

<br />

### UpdateBooking

`PUT /api/bookings`

```json
{
  "notes": "Some notes about booking 2"
}
```

<br />

### Delete Booking

`DELETE /api/bookings/:id`

<br />





