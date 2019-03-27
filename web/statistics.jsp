<%--
Created by IntelliJ IDEA.
User: Leda
Date: 21/9/2015
Time: 11:24 μμ
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>

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
                <li><a href="stayPoints.jsp"><i class="fa fa-map-marker"></i>  Stay Points </a></li>
                <li><a href="pointsOfInterest.jsp"><i class="fa fa-map-marker"></i>  Points of Interest</a></li>
                <li class="selected"><a href="statistics.jsp"><i class="fa fa-bar-chart"></i>  Statistics</a></li>

            </ul>
            <ul class="nav navbar-nav navbar-right navbar-user">
            </ul>
        </div>
    </nav>

    <div id="page-wrapper">
        <!-- Statistics -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><i class="fa fa-bar-chart-o"></i>Users of mobile phone operators </h3>
                    </div>
                    <div class="panel-body">
                        <div id="operators-diagram"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><i class="fa fa-bar-chart-o"></i> Users with the lowest battery level per hour (sum of all days) </h3>
                    </div>
                    <div class="panel-body">
                        <div id="lowBatteryHour"></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><i class="fa fa-bar-chart-o"></i> Users with the lowest battery level per day per hour  </h3>
                    </div>
                    <div class="panel-body">
                        <div id="lowBattery"></div>
                    </div>
                </div>
            </div>
        </div>

        </div>
    </div>
</div>

<script>
    window.onload = function() {
       // loadDiagrams();
        createLowBatterDiagram();
        createLowBatteryHourDiagram();
        createOperatorsDiagram();

    };
    function createLowBatteryHourDiagram() {
        $.ajax({
            type: "POST",
            url: "http://localhost:8081/batteryLowHour",
            dataType: "text",
            success: function (json) {

                var data = json2array(JSON.parse(json));
                var hours = [];
                var users = [];
                for (var i = 0; i < data.length; i++) {
                    hours.push(data[i][0] + ":00");
                    users.push(data[i][1]);
                }

                $("#lowBatteryHour").shieldChart({
                    theme: "dark",
                    primaryHeader: {
                        text: "Number of users with the lowest battery level < 15% per hour (sum of all days)"
                    },
                    exportOptions: {
                        image: false,
                        print: false
                    },
                    axisX: {
                        categoricalValues: hours,
                        title: {
                            text: "Hours of the day"
                        }

                    },
                    axisY: {

                        title: {
                            text: "Number of users"
                        }
                    },
                    seriesSettings: {
                        bar: {
                            color: "pink"
                        }
                    },
                    tooltipSettings: {
                        chartBound: true,
                        axisMarkers: {
                            enabled: true,
                            mode: 'x'
                        }
                    },
                    dataSeries: [{
                        seriesType: 'bar',
                        collectionAlias: "Number of users",
                        data: users
                    }]
                });
            },
            error: function (error) {
//                alert("Request failed");
            }
        });
    }
    function createLowBatterDiagram() {

        $.ajax({
            type: "POST",
            url: "http://localhost:8081/batteryLow",
            dataType: "text",
            success: function (json) {

                var data = json2array(JSON.parse(json));

                var hours = [];
                var users = [];
                for (var i = 0; i < data.length; i++) {
                    hours.push(data[i][0] + ":00");
                    users.push(data[i][1]);
                }
                $("#lowBattery").shieldChart({
                    theme: "dark",
                    zoomMode: "x",
                    primaryHeader: {
                        text: "Number of users with the lowest battery level < 15% per hour per day"
                    },
                    exportOptions: {
                        image: false,
                        print: false
                    },
                    axisX: {
                        categoricalValues: hours,
                        axisTickText: {

                            step: 72

                        },
                    },
                    axisY: {

                        title: {
                            text: "Number of users"
                        }
                    },
                    seriesSettings: {
                        bar: {
                            color: "lightgreen"
                        }
                    },
                    tooltipSettings: {
                        chartBound: true,
                        axisMarkers: {
                            enabled: true,
                            mode: 'x'
                        }
                    },
                    dataSeries: [{
                        seriesType: 'area',
                        color: "lightgreen",
                        collectionAlias: "Number of users",
                        data: users
                    }]

                });
            },
            error: function (error) {
//                alert("Request failed");
            }
        });
    }
    function createOperatorsDiagram() {
        $.ajax({
            type: "POST", //GET
            url: "http://localhost:8081/operators",
            dataType: "text",
            success: function (json) {

                var data = json2array(JSON.parse(json));
                var operators = [];
                var users = [];
                for (var i = 0; i < data.length; i++) {
                    operators.push(data[i][0]);
                    users.push(data[i][1]);
                }

                $("#operators-diagram").shieldChart({
                    theme: "dark",
                    primaryHeader: {
                        text: "Number of users of every mobile phone operator"
                    },
                    exportOptions: {
                        image: false,
                        print: false
                    },
                    axisY: {

                        title: {
                            text: "Number of users"
                        }
                    },
                    axisX: {
                        categoricalValues: ["Operator"]
                    },
                    dataSeries: [{
                        seriesType: 'bar',
                        data: [users[2]],
                        collectionAlias: operators[2],
                    }, {
                        seriesType: 'bar',
                        collectionAlias: operators[3],
                        data: [users[3]]
                    }, {
                        seriesType: 'bar',
                        collectionAlias: operators[4],
                        data: [users[4]]
                    }, {
                        seriesType: 'bar',
                        collectionAlias: operators[6],
                        data: [users[6]]
                    }, {
                        seriesType: 'bar',
                        collectionAlias: operators[5],
                        data: [users[5]]
                    }, {
                        seriesType: 'bar',
                        collectionAlias: operators[7],
                        data: [users[7]]
                    }, {
                        seriesType: 'bar',
                        collectionAlias: operators[0],
                        data: [users[0]]
                    }, {
                        seriesType: 'bar',
                        collectionAlias: operators[1],
                        data: [users[1]]
                    }

                    ]
                });

            },
            error: function (error) {
//                alert("Request failed");
            }
        });
    }
    function json2array(json){
        var result = [];
        var keys = Object.keys(json);
        keys.forEach(function(key){
            var result1 = [];
            result1.push(key);
            result1.push(json[key]);
            result.push(result1);
        });
        return result;
    }

</script>


</body>
</html>
