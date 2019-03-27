<%--
Created by IntelliJ IDEA.
User: Leda
Date: 21/9/2015
Time: 11:24 μμ
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.java.readers.UserReader" %>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Mobile Master</title>
  <link rel="shortcut icon" type="image/x-icon" href="http://wacxtv.com/portals/18/Images/antenna_icon.png" />

  <!-- Bootstrap -->
  <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css" />
  <link rel="stylesheet" type="text/css" href="font-awesome/css/font-awesome.min.css" />
  <link rel="stylesheet" type="text/css" href="css/local.css" />

  <script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
  <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>

  <!-- Shieldui for charts -->
  <link rel="stylesheet" type="text/css" href="http://www.shieldui.com/shared/components/latest/css/shieldui-all.min.css" />
  <link rel="stylesheet" type="text/css" href="http://www.shieldui.com/shared/components/latest/css/light-bootstrap/all.min.css" />
  <link id="gridcss" rel="stylesheet" type="text/css" href="http://www.shieldui.com/shared/components/latest/css/dark-bootstrap/all.min.css" />

  <script type="text/javascript" src="http://www.shieldui.com/shared/components/latest/js/shieldui-all.min.js"></script>
  <script type="text/javascript" src="http://www.prepbootstrap.com/Content/js/gridData.js"></script>

  <!-- DatePicker -->
  <script type="text/javascript" src="js/bootstrap-datepicker.js"></script>
  <link rel="stylesheet" type="text/css" href="css/datepicker.css" />

  <!-- Google Maps-->
  <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?v=3.11&ssensor=false"></script>
  <script type="text/javascript" src="js/my-googlemaps-functions.js"></script>

</head>
<body>
<div id="wrapper">
  <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="index.jsp">Mobile Master</a>
    </div>
    <div class="collapse navbar-collapse navbar-ex1-collapse">
      <ul id="active" class="nav navbar-nav side-nav">
        <li><a href="index.jsp"><i class="fa fa-share-alt"></i> Data Visualization</a></li>
        <li><a href="accessPointsOptimal.jsp"><i class="fa fa-map-marker"></i> Optimal Access Points</a></li>
        <li class = "selected"><a href="stayPoints.jsp"><i class="fa fa-map-marker"></i>  Stay Points </a></li>
        <li><a href="pointsOfInterest.jsp"><i class="fa fa-map-marker"></i>  Points of Interest</a></li>
        <li><a href="statistics.jsp"><i class="fa fa-bar-chart"></i>  Statistics</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right navbar-user">

      </ul>
    </div>
  </nav>

  <div id="page-wrapper">
    <div class="row">
      <div class="col-lg-12">
        <%--<h1>Dashboard <small>Statistics and more</small></h1>--%>
        <div class="alert alert-dismissable alert-warning">
          <button data-dismiss="alert" class="close" type="button">&times;</button>
          Select a user, a timeframe,minimum and maximum time difference as well as maximum spatial distance to discover the Stay Points!
        </div>
      </div>
    </div>


    <div class ="row">
      <div class="col-lg-12">
        <div class="panel panel-primary">
          <div class="panel-heading">
            <h3 class="panel-title"><i class="fa fa-search"></i>  Search  </h3>
          </div>

          <div class="panel-body">
            <div class="form-group">
              <div class = "row">

              <!--<form method="GET">-->
              <%--<form method = "POST">--%>
              <div class ="col-md-3">
                <div class="hero-unit">
                  <i class="fa fa-calendar"></i> Select Start Date
                  <input  type="text" class="form-control" placeholder= 'Start Date' id="start">
                </div>
              </div>
              <div class ="col-md-3">
                <div class="hero-unit">
                  <i class="fa fa-calendar"></i> Select End Date
                  <input  type="text"  class="form-control" placeholder= 'End Date' id="end">
                </div>
              </div>
              <script type="text/javascript">
                $(document).ready(function () {

                  $('#start').datepicker({
                    format: "dd/mm/yyyy",
                    autoclose: true,
                    startDate: '09/09/2015',
                    endDate: '30/09/2015',
                    datesDisabled: ['10/09/2015']
                  });
                  $('#end').datepicker({
                    format: "dd/mm/yyyy",
                    datesDisabled: ["10/01/2015", "09/30/2015"],
                    viewSelect:'hour'
                  });

                });
              </script>
              <div class ="col-md-3">
                <div class="hero-unit">
                  <!-- User -->
                  <i class="fa fa-user"></i> Select User
                  <!----------->
                  <select class="form-control" id="sel1" name="user">
                    <%! UserReader userReader = new UserReader(); %>
                    <% userReader.findUsers();%>
                    <option disabled selected>--Select user--</option>
                    <%  for (String user: userReader.getUsers()){ %>
                    <option><%= user %></option>
                    <% } %>
                  </select>
                </div>
              </div>

              </div>
              <br/>
              <div class = "row">
                <div class ="col-md-3">
                  <div class="hero-unit">
                    <i class="fa fa-clock-o"></i> Minimum Time Difference:
                    <input  type="text" class="form-control" placeholder= 'e.g. 30' id="Tmin">
                  </div>
                </div>
                <div class ="col-md-3">
                  <div class="hero-unit">
                    <i class="fa fa-clock-o"></i> Maximum Time Difference:
                    <input  type="text" class="form-control" placeholder= 'e.g. 180' id="Tmax">
                  </div>
                </div>
                <div class ="col-md-3">
                  <div class="hero-unit">
                    <span class="glyphicon glyphicon-resize-full"></span> Maximum Space Difference:
                    <input  type="text" class="form-control" placeholder= 'e.g. 300' id="Dmax">
                </div>
                </div>

              <div class ="col-md-3">
                <br>
                <input type="submit" class="btn btn-success " value="Search" onclick="jsSelectFunction()">
                <input type="submit" class="btn btn-default" value="Clear Map" onclick="jsClearFunction()">
              </div>
              </div>

              </div>
            </div>
          </div>
        </div>

      </div>
    </div>

    <!-- My row-->
    <div class ="row">
      <div class="col-md-12">

        <div class="panel panel-primary">
          <div class="panel-heading">
            <h3 class="panel-title"><i class="fa fa-globe"></i>  Map </h3>

          </div>
          <div class="panel-body">
            <div id="map-canvas"></div>
          </div>
        </div>
      </div>
    </div>
    <!-- Statistics -->
  </div>
</div>
<!-- /#wrapper -->

<script>
  function jsClearFunction() {
    clearMarkers();
    clearRoute();
  }
  function jsSelectFunction() {
    var user = document.getElementById('sel1').value;
    var startDate = $('#start').datepicker({dateFormat: 'dd,MM,yyyy'}).val();
    var endDate = $('#end').datepicker({dateFormat: 'dd,MM,yyyy'}).val();
    var Tmin = document.getElementById('Tmin').value;
    var Tmax = document.getElementById('Tmax').value;
    var Dmax = document.getElementById('Dmax').value;

    $.ajax({
      type: "GET",
      url: "http://localhost:8081/stayPoints/stayPoint?user=" + user + "&start=" + startDate + "&end=" + endDate + "&Tmin=" + Tmin + "&Tmax=" + Tmax + "&Dmax=" + Dmax,
      data: {"user": user, "startDate": startDate, "endDate": endDate, "Tmin": Tmin, "Tmax": Tmax, "Dmax": Dmax},
      dataType: "text",
      success: function (json) {
        var geojson = JSON.parse(json);
        var coordinates = geojson.features[0].geometry.coordinates;
        addStayPoints(coordinates);
       // "coordinates": [ [102.0, 0.0], [103.0, 1.0] ..

      },
      error: function (error) {
        alert("Request failed");
      }
    });
  }
</script>

</body>
</html>
