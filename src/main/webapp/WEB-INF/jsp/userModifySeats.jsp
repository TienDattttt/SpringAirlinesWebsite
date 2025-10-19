<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
    xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" layout:decorate="~{template}"
    xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity5">

<head>
    <!-- css files -->
    <link th:href="@{/css/seats.css}" rel="stylesheet" />
    <link th:href="@{/css/adminMenu.css}" rel="stylesheet" />

    <!-- js files -->
    <script type="text/javascript" th:src="@{/js/seats.js}"></script>

    <script>
        $(document).ready(function () {
            $('ul li').on('click', function () {
                $('li').removeClass('active');
                $(this).addClass('active');
            });
        });

        document.addEventListener('DOMContentLoaded', function () {

            var initSelectedSeats = document.getElementById('selectedSeats').value;
            if (initSelectedSeats) {

                var seatsArray = initSelectedSeats.split(',');


                seatsArray.forEach(function (seat) {

                    var seatParts = seat.split('-');
                    var row = seatParts[0];
                    var seatNum = seatParts[1];
                    var flightId = seatParts[2];

                    var seatElement = document.querySelector('.seat[data-row="' + row + '"][data-seat="' + seatNum + '"][data-flight="' + flightId + '"]');
                    if (seatElement) {
                        seatElement.classList.add('selected-seat');
                    }
                });
            }
        });
    </script>
    <script th:inline="javascript">

        $(document).ready(function () {
            $('#seatsNowTaken').hide();
            addSelectedSeatClass();

            updateSeatAvailability();

            setInterval(updateSeatAvailability, 3000);

            handleSeatSelection();
        });
    </script>
</head>

<body>
    <div layout:fragment="content">
        <div class="container">
            <h2 class="fw-bold mb-4 text-center">Sửa đổi chỗ ngồi</h2>
        </div>

        <input type="hidden" id="numOfPassengers" th:value="${numOfPassengers}" />
        <input type="hidden" id="numOfFlights" th:value="${numOfFlights}" />

        <div class="container-fluid">
            <div class="row">
                <!-- side menu -->
                <nav class="sidebar-navigation">
                    <ul style="list-style: none;">
                        <li><a href="/userModifyLuggage"> <i class="fa-solid fa-suitcase-rolling"></i> <span
                                    class="tooltip">Hành lý</span>
                            </a></li>
                        <li class="active"><a href="/userModifySeats"> <i class="fa-solid fa-chair"></i> <span
                                    class="tooltip">Chỗ ngồi</span>
                            </a></li>
                    </ul>
                </nav>


                <div class="col col-8">
                    <div class="container">

                        <div class="row">
                            <div th:each="flight, flightIndex : ${flightList}" class="col-lg-5 col-md-4 mb-5">


                                <div class="col">
                                    <h4 class="text-primary">
                                        ID Chuyến bay: <span th:text="${flight.id}"></span>
                                    </h4>
                                    <p class="text-muted">Chọn chỗ ngồi cho mỗi hành khách:</p>

                                    <div th:each="passenger, passIndex : ${passengerListDTO.passengerList}">

                                        <div class="row align-items-center">
                                            <div class="col">
                                                <span
                                                    th:text="${passenger.firstName + ' ' + passenger.lastName}"></span>
                                            </div>
                                            <div class="col">
                                                <button type="button" class="btn btn-light mt-2 passenger-button"
                                                    th:data-index="${passIndex.index + '-' + flightIndex.index}">
                                                    <i class="fa-solid fa-user-large"></i> <span
                                                        th:text="${#strings.toUpperCase(passenger.firstName.substring(0,1))} + ${#strings.toUpperCase(passenger.lastName.substring(0,1))}"></span>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <br /> <br />

                                <div class="col">
                                    <div th:each="flightDTO : ${flightDTOList}"
                                        th:if="${flightDTO.flight.id == flight.id}">
                                        <div class="plane" th:data-flight-id="${flight.id}">
                                            <br /> <br /> <br />
                                            <div th:each="row, rowIdx : ${flightDTO.seatMatrix}">
                                                <div th:each="seat, seatStat : ${row}" class="seat-container">
                                                    <span th:if="${seatStat.index == 0}" class="row-counter"
                                                        th:text="${rowIdx.index + 1}"></span>
                                                    <button class="seat"
                                                        th:attr="data-row=${seat.row}, data-seat=${seat.seat}, data-flight=${flightIndex.index}">
                                                        <span th:each="passenger : ${passengerListDTO.passengerList}"
                                                            th:if="${#strings.equals(#strings.toString(passenger.flightAndSeats[flight.id]), #strings.toString(seat.row + '-' + seat.seat))}"
                                                            th:text="${#strings.toUpperCase(passenger.firstName.substring(0,1))} + ${#strings.toUpperCase(passenger.lastName.substring(0,1))}"></span>
                                                    </button>
                                                    <div th:classappend="${seatStat.index == 0} ? 'seat-number-most-left' : 'seat-number'"
                                                        th:text="${seat.row + '-' + seat.seat}"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div id="seatsNowTaken" class="alert alert-warning alert-dismissible" role="alert">
                        <span class="close" data-dismiss="alert" aria-label="Close"><span
                                aria-hidden="true">&times;</span></span> Rất tiếc, một số chỗ ngồi bạn đã chọn hiện đã
                        được đặt... vui lòng chọn lại.
                    </div>
                    <div class="d-flex align-items-center">
                        <form method="post" th:action="@{/userModifySeats/ProcessModifySeats}">

                            <input type="hidden" id="selectedSeats" name="selectedSeats"
                                th:value="${initSelectedSeats}" />

                            <button type="submit" class="btn btn-outline-primary rounded-0">
                                <i class="fa-regular fa-pen-to-square me-2"></i>Lưu
                            </button>
                        </form>
                    </div>
                    <span th:if="${error_message != null}" class="text-danger" th:text="${error_message}"> </span> <span
                        th:if="${success_message != null}" class="text-success" th:text="${success_message}"> </span>
                </div>
            </div>
        </div>
    </div>
</body>

</html>