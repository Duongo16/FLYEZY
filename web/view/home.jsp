<%-- 
    Document   : Home
    Created on : Sep 14, 2024, 10:08:31 PM
    Author     : Admin
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Location"%>
<%@page import="model.Airport"%>
<%@page import="dal.LocationDAO"%>
<%@page import="dal.AirportDAO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" type="image/png" href="img/flyezy-logo3.png" />
        <title>Flight Booking Form</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Bootstrap Icons -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <!-- Bootstrap Datepicker CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css">
        <style>
            .flight-form {
                max-width: 1000px;
                margin: 50px auto;
                padding: 20px;
            }
            .flight-type {
                display: flex;
                align-items: center;
            }
            .form-control.read-only {
                background-color: #e9ecef;
                cursor: not-allowed;
            }
            .btn-search {
                background-color: #28a745;
                color: white;
                padding: 10px 20px;
                font-size: 18px;
            }
            .discount-code-container {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .discount-code-container input {
                width: 30%;
            }
            .icon {
                font-size: 24px;
                color: green;
            }
            .btn-search {
                width: 200px;
                font-size: 18px;
            }
            .location-list {
                max-height: 200px;
                overflow-y: auto;
            }

            .location-item:hover {
                background-color: #f0f0f0;
            }
            .title-section {
                text-align: left;            /* Center the title */
                padding: 15px 25px;            /* Increase padding for more space */
                font-weight: bold;             /* Bold the title */
                font-size: 24px;               /* Larger font size */
                color: #333;                   /* Text color */
                border: 2px solid #ccc;     /* Blue border for the title */
                width: 15%;
                background-color: #f1f1f1;     /* Light background color */

            }

        </style>
    </head>
    <body>
        <%
    LocationDAO locationDao = new LocationDAO();
    AirportDAO airportDao = new AirportDAO();

        %>
        <%@include file="header.jsp" %> 
        <section>
            <div class="container flight-form">
                <div class="banner" style="position: relative; overflow: hidden; background-image: url('img/Messi.jpg'); background-size: cover; padding: 10%; color: white;width: 142.2%;transform: translate(-15.1%, -2.7%);height: 800px">



                    <form action="flightTickets" method="GET" class="row g-1" onsubmit="return validateLocations(event)">
                        <!-- Title -->
                        <div class="title-section" style="transform: translateY(10%)">
                            <h2>Đặt Chuyến Bay</h2>
                        </div>

                        <div class="form-container" style="border: 2px solid #ccc; padding: 2%;  background-color: #f9f9f9; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); height: 170px">

                            <div class="row form-input">
                                <h1 id="errorMessage" style="color: red;"></h1> <!-- Display the error message here -->
                                <div class="row" style="transform: translateX(0.5%)">
                                    <label class="col-md-1">
                                        <input type="radio" id="oneWay" name="tripType" value="oneWay" style="transform: scale(1.5);" checked onclick="toggleReturnDate()">
                                        <span style="color: activecaption; font-size: 130%;margin-left: 5%;">Một chiều</span>
                                    </label>
                                    <label class="col-md-1">
                                        <input type="radio" id="roundTrip" name="tripType" value="roundTrip" style="transform: scale(1.5);" onclick="toggleReturnDate()">
                                        <span style="color: activecaption; font-size: 130%;margin-left: 5%;">Khứ hồi</span>
                                    </label>
                                </div>
                                <!-- From Field -->
                                <div class="col-md-2">
                                    <label for="from" class="col-form-label text-uppercase" style="color: activecaption">Từ</label>
                                    <input type="text" style="height: 80%;font-size: 150%" class="form-control" id="fromDisplay" onclick="showLocationList('from')" oninput="filterLocations('from')" required>
                                    <input type="hidden" id="from" name="departure">
                                    <div id="from-locations" class="location-list" style="display:none; position:absolute; background-color:white; border:1px solid #ccc; z-index:1000;">
                                        <%                
                                            for(Location l : locationDao.getAllLocation()) {
                                                for(Airport a : airportDao.getAllAirport()){
                                                    if(a.getLocationId() == l.getId()){
                                        %>
                                        <div class="location-item" onclick="selectLocation('<%= a.getId() %>', '<%= l.getName() %>', 'from')" style="padding: 5px; cursor: pointer;">
                                            <span style="font-weight: bold; font-size: 16px; color: black;">
                                                <%= l.getName() %>
                                            </span>
                                            <span style="font-size: 14px; color: grey; filter: blur(1%); margin-left: 5px;">
                                                <%= a.getName() %>
                                            </span>
                                        </div>
                                        <% } } } %>
                                    </div>
                                </div>

                                <!-- To Field -->
                                <div class="col-md-2">
                                    <label for="to" class="col-form-label text-uppercase" style="color: activecaption">Tới</label>
                                    <input type="text" style="height: 80%;font-size: 150%" class="form-control" id="toDisplay" onclick="showLocationList('to')" oninput="filterLocations('to')" required>
                                    <input type="hidden" id="to" name="destination">
                                    <div id="to-locations" class="location-list" style="display:none; position:absolute; background-color:white; border:1px solid #ccc; z-index:1000;">
                                        <%                
                                            for(Location l : locationDao.getAllLocation()) {
                                                for(Airport a : airportDao.getAllAirport()){
                                                    if(a.getLocationId() == l.getId()){
                                        %>
                                        <div class="location-item" onclick="selectLocation('<%= a.getId() %>', '<%= l.getName() %>', 'to')" style="padding: 5px; cursor: pointer;">
                                            <span style="font-weight: bold; font-size: 16px; color: black;">
                                                <%= l.getName() %>
                                            </span>
                                            <span style="font-size: 14px; color: grey; filter: blur(1%); margin-left: 5px;">
                                                <%= a.getName() %>
                                            </span>
                                        </div>
                                        <% } } } %>
                                    </div>
                                </div>

                                <!-- Departure Date Field -->
                                <div class="col-md-2">
                                    <label for="departureDate" class="col-form-label text-uppercase" style="color: activecaption">Ngày đi</label>
                                    <input type="text" class="form-control" id="departureDate" name="departureDate" style="height: 80%;font-size: 150%;" placeholder="Ngày đi" required>
                                </div>
                                <div class="col-md-2" id="returnDateField" style="display:none;">
                                    <label for="returnDate" class="col-form-label text-uppercase" style="color: activecaption">Ngày về:</label>
                                    <input type="text" id="returnDate" class="form-control" name="returnDate" style="height: 80%;font-size: 150%;" placeholder="Ngày về" required>
                                </div>

                                <!-- Passengers Field -->
                                <div class="col-md-4" id="passengerField" style="position: relative;">
                                    <label for="passengers" class="col-form-label text-uppercase" style="color: activecaption">Hành khách</label>
                                    <input type="number" style="height: 80%;width: 100%; font-size: 150%;" class="form-control" id="passengers" value="1" min="1" max="10" required onclick="togglePassengerOptions()" readonly>
                                    <div id="passenger-options" class="passenger-options" style="display: none; width: 600px;height: auto;position: absolute; top: 100%; left: 0; border: 2px solid #ccc; padding: 10px; border-radius: 5px; margin-top: 30px;background-color: white; z-index: 1000;transform: translateX(-10%)">
                                        <div class="row" style="display: flex; justify-content: space-evenly; margin-top: 1%;padding: 3%;">
                                            <div class="col-md-4">
                                                <label for="adult-count" style="color: activecaption;height: 80%; font-size: 150%;">Người lớn:</label>
                                                <input type="number" id="adult-count" name="adult" value="1" min="1" max="10" style="color: activecaption;height: 50%;width: 40%; font-size: 150%;">
                                            </div>
                                            <div class="col-md-4">
                                                <label for="child-count" style="color: activecaption; height: 80%; font-size: 150%;">Trẻ em:</label>
                                                <input type="number" id="child-count" name="child" value="0" min="0" max="9" style="color: activecaption;height: 50%;width: 40%; font-size: 150%;">
                                                <br>
                                                <p style="transform: translateY(-150%);color: activecaption;">(2-11 tuổi)</p>
                                            </div>
                                            <div class="col-md-4">
                                                <label for="infant-count" style="color: activecaption;height: 80%; font-size: 150%;">Trẻ sơ sinh:</label>
                                                <input type="number" id="infant-count" name="infant" value="0" min="0" max="5" style="color: activecaption;height: 50%;width: 40%; font-size: 150%;">
                                                <br>
                                                <p style="transform: translateY(-150%);color: activecaption;">(0-2 tuổi)</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Submit Button -->
                                <div class="col-md-2" style="margin-top: 2%">
                                    <button type="submit" style="height:140%;width: 80%;font-size: 150%" class="btn btn-success">Tìm Chuyến Bay</button>
                                </div>
                            </div>
                        </div>
                    </form>



                </div>
            </div>
        </section>

        <%@include file="footer.jsp" %> 
        <!-- Bootstrap JS (optional for Bootstrap features) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <script>
                                        const passengersInput = document.getElementById('passengers');
                                        const adultCountInput = document.getElementById('adult-count');
                                        const childCountInput = document.getElementById('child-count');
                                        const infantCountInput = document.getElementById('infant-count');
                                        const passengerOptionsDiv = document.getElementById('passenger-options');

                                        // Function to update total passengers
                                        function updateTotalPassengers() {
                                            const adults = parseInt(adultCountInput.value) || 0;
                                            const children = parseInt(childCountInput.value) || 0;
                                            const infants = parseInt(infantCountInput.value) || 0;
                                            const totalPassengers = adults + children + infants;
                                            passengersInput.value = totalPassengers;
                                            // Validation checks
                                            if (infants > adults) {
                                                alert("The number of infants cannot exceed the number of adults.");
                                                infantCountInput.value = adults; // Adjust infants to equal adults
                                                passengersInput.value -= 1;
                                                return;
                                            }

                                            if (totalPassengers > 10) {
                                                alert("Total passengers cannot exceed 10.");
                                                passengersInput.value = 10; // Set to max allowed
                                                return; // Stop further processing
                                            }

                                            // If the total reaches 10, lock the ability to add more passengers
                                            if (totalPassengers === 10) {
                                                const remainingSpots = 10 - (adults + children + infants);
                                                adultCountInput.max = adults; // Prevent increasing adults
                                                childCountInput.max = children; // Prevent increasing children
                                                infantCountInput.max = infants; // Prevent increasing infants
                                            } else {
                                                // Allow to increase if total passengers < 10
                                                adultCountInput.max = Math.min(10 - (children + infants), 10);
                                                childCountInput.max = Math.min(10 - (adults + infants), 9); // Assume max 9 children
                                                infantCountInput.max = Math.min(10 - (adults + children), 5); // Assume max 5 infants
                                            }

                                        }

                                        // Event listeners for the input fields
                                        adultCountInput.addEventListener('input', updateTotalPassengers);
                                        childCountInput.addEventListener('input', updateTotalPassengers);
                                        infantCountInput.addEventListener('input', updateTotalPassengers);

                                        // Show passenger options when the main input is focused
                                        passengersInput.addEventListener('focus', () => {
                                            passengerOptionsDiv.style.display = 'block';
                                        });


                                        // Show options when focusing on the main input
                                        passengersInput.addEventListener('focus', () => {
                                            passengerOptionsDiv.style.display = "block";
                                        });

                                        // Hide options when clicking outside the input and options
                                        document.addEventListener('click', (event) => {
                                            const isClickInsideOptions = passengerOptionsDiv.contains(event.target);
                                            const isClickOnInput = event.target === passengersInput;

                                            if (!isClickInsideOptions && !isClickOnInput) {
                                                passengerOptionsDiv.style.display = "none"; // Hide if click is outside
                                            }
                                        });

                                        function showLocationList(inputId) {
                                            // Hide all location lists first
                                            hideAllLocationLists();

                                            // Display the current location list for the clicked input
                                            document.getElementById(inputId + '-locations').style.display = 'block';
                                        }

                                        function hideAllLocationLists() {
                                            // Get all location lists and hide them
                                            const locationLists = document.querySelectorAll('.location-list');
                                            locationLists.forEach(list => {
                                                list.style.display = 'none';
                                            });
                                        }

                                        function selectLocation(locationId, displayText, inputId) {
                                            // Set the visible input value to the selected location - airport text
                                            document.getElementById(inputId + 'Display').value = displayText;

                                            // Set the hidden input value to the selected locationId
                                            document.getElementById(inputId).value = locationId;

                                            // Hide the location list after selection
                                            document.getElementById(inputId + '-locations').style.display = 'none';


                                        }

                                        // Filter locations based on input value
                                        function filterLocations(type) {
                                            const input = document.getElementById(type + 'Display');
                                            const filter = input.value.toLowerCase();
                                            const locationList = document.getElementById(type + '-locations');
                                            const items = locationList.getElementsByClassName('location-item');

                                            // Loop through all items and hide those that don't match the input
                                            for (let i = 0; i < items.length; i++) {
                                                const txtValue = items[i].textContent || items[i].innerText;
                                                if (txtValue.toLowerCase().indexOf(filter) > -1) {
                                                    items[i].style.display = "";
                                                } else {
                                                    items[i].style.display = "none";
                                                }
                                            }

                                            // Show the location list only if there are items visible
                                            if (filter.length > 0) {
                                                locationList.style.display = 'block';
                                            } else {
                                                locationList.style.display = 'none';
                                            }
                                        }


                                        // Hide the list if the user clicks outside of the input or list
                                        document.addEventListener('click', function (event) {
                                            // If the clicked element is not an from or to or part of the location list, hide all lists
                                            if (!event.target.closest('.location-list') && !event.target.closest('#fromDisplay') && !event.target.closest('#toDisplay')) {
                                                document.querySelectorAll('.location-list').forEach(list => {
                                                    list.style.display = 'none';
                                                });
                                            }
                                        });
                                        function validateLocations(event) {
                                            const departure = document.getElementById('fromDisplay').value;
                                            const destination = document.getElementById('toDisplay').value;
                                            const errorMessageElement = document.getElementById('errorMessage');

                                            // Check if departure and destination are the same
                                            if (departure === destination) {
                                                event.preventDefault(); // Prevent form submission

                                                // Display error message
                                                errorMessageElement.textContent = "Điểm đi và điểm đến không được giống nhau.";
                                                errorMessageElement.style.color = "red"; // Optional: Add styling to the error message

                                                return false; // Prevent the form from submitting
                                            }

                                            // Clear the error message if validation passes
                                            errorMessageElement.textContent = "";
                                            return true; // Allow form submission if locations are different
                                        }
                                        function toggleReturnDate() {
                                            const returnDateField = document.getElementById("returnDateField");
                                            const tripType = document.querySelector('input[name="tripType"]:checked').value; // Get the value of the selected trip type
                                            const passengerField = document.getElementById("passengerField");

                                            // If "Khứ hồi" is selected, display the "Ngày về" field
                                            if (tripType === "roundTrip") {
                                                returnDateField.style.display = "block";
                                                passengerField.className = "col-md-2"; // Set passengers field to col-md-2
                                            } else if(tripType === "oneWay"){
                                                returnDateField.style.display = "none";
                                                passengerField.className = "col-md-4"; // Set passengers field to col-md-4
                                            }
                                        }
                                        // Assuming you have jQuery and Bootstrap Datepicker included
                                        $(document).ready(function () {
                                            $('#departureDate').datepicker({
                                                format: 'yyyy-mm-dd', // Custom date format
                                                autoclose: true, // Automatically close the calendar after picking a date
                                                todayHighlight: true, // Highlight today's date
                                                orientation: 'bottom auto' // Ensure the calendar pops up below the input
                                            });
                                        });
                                        $(document).ready(function () {
                                            $('#returnDate').datepicker({
                                                format: 'yyyy-mm-dd', // Custom date format
                                                autoclose: true, // Automatically close the calendar after picking a date
                                                todayHighlight: true, // Highlight today's date
                                                orientation: 'bottom auto' // Ensure the calendar pops up below the input
                                            });
                                        });
        </script>


        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

        <!-- Bootstrap Datepicker JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
    </body>
</html>




