/**
 * Created by Leda on 4/10/2015.
 */

google.maps.event.addDomListener(window, 'load', initialize);


/* map of Athens */
var athens;
var map;
var infowindow;

function initialize() {

    /* Customize map */
    var styles = [
        {
            "featureType": "water",
            "elementType": "geometry",
            "stylers": [
                {
                    "hue": "#71ABC3"
                },
                {
                    "saturation": -10
                },
                {
                    "lightness": -21
                },
                {
                    "visibility": "simplified"
                }
            ]
        },
        {
            "featureType": "landscape.natural",
            "elementType": "geometry",
            "stylers": [
                {
                    "hue": "#7DC45C"
                },
                {
                    "saturation": 37
                },
                {
                    "lightness": -41
                },
                {
                    "visibility": "simplified"
                }
            ]
        },
        {
            "featureType": "landscape.man_made",
            "elementType": "geometry",
            "stylers": [
                {
                    "hue": "#C3E0B0"
                },
                {
                    "saturation": 23
                },
                {
                    "lightness": -12
                },
                {
                    "visibility": "simplified"
                }
            ]
        },
        {
            "featureType": "poi",
            "elementType": "all",
            "stylers": [
                {
                    "hue": "#A19FA0"
                },
                {
                    "saturation": -98
                },
                {
                    "lightness": -20
                },
                {
                    "visibility": "off"
                }
            ]
        },
        {
            "featureType": "road",
            "elementType": "geometry",
            "stylers": [
                {
                    "hue": "#FFFFFF"
                },
                {
                    "saturation": -100
                },
                {
                    "lightness": 100
                },
                {
                    "visibility": "simplified"
                }
            ]
        }
    ];

    var shape = {
        coords: [1, 1, 1, 20, 18, 20, 18 , 1],
        type: 'poly'
    };

    /* Create map of Athens */
    athens = new google.maps.LatLng(37.9667, 23.7167);

    map= new google.maps.Map(document.getElementById('map-canvas'), {
        center: athens,
        zoom: 12,
        mapTypeId: google.maps.MapTypeId.TERRAIN,
        styles: styles
    });

     infowindow = new google.maps.InfoWindow({
        maxWidth: 160,
         color: '#000000',
    });


};

/* Access Points */

var accessPoints = [];
var APinfo = [];
var imageAP = {
    url: 'http://www.realtyonegroup.com/images/map_marker_dot.png'
};
var markersAP = [];

function addAccessPoints(geojson) {

    for (var i = 0; i < geojson.features.length; i++) {
        //coordinates of each access point
        var coordinates = geojson.features[i].geometry.coordinates;
        accessPoints.push(new google.maps.LatLng(coordinates[0], coordinates[1]));
        var ssid = geojson.features[i].properties.ssid;
        var meanRSSI = geojson.features[i].properties.meanRSSI;
        var frequency = geojson.features[i].properties.frequency;

        APinfo[i] = "<div> "
            + "<b>RSSI: </b>" + meanRSSI + "<br/>"
            + "<b>Frequency: </b>" + frequency + "<br/>"
            + "</div>";

    }
    //alert(accessPoints.length + " access points found!!!");

    /*Create and drop */
    for (var i = 0; i < accessPoints.length; i++) {
        addMarkerWithInfo(accessPoints[i], i *1,APinfo[i],imageAP,markersAP);
    }
}


/* Optimal Access Points */

var accessPointsOptimal = [];
var APOinfo = [];
var imageAPO = {
    url: 'http://maps.google.com/mapfiles/kml/paddle/red-stars-lv.png'
};
var imageAPOpt = {
    url: 'http://maps.google.com/mapfiles/kml/paddle/red-stars-lv.png'
}
var markersAPO =[];

function addOptimalAccessPoints(geojson){

    for (var i = 0; i < geojson.features.length; i++) {
        //coordinates of each access point
        var coordinates = geojson.features[i].geometry.coordinates;
        accessPointsOptimal.push(new google.maps.LatLng(coordinates[0], coordinates[1]));
        var ssid = geojson.features[i].properties.ssid;
        var meanRSSI = geojson.features[i].properties.meanRSSI;
        var frequency = geojson.features[i].properties.frequency;

        APOinfo[i] = "<div> "
            + "<b>RSSI: </b>" + meanRSSI + "<br/>"
            + "<b>Frequency: </b>" + frequency + "<br/>"
            + "</div>";

    }
    //alert(accessPointsOptimal.length + " optimal access points found!!!");

    /*Create and drop */
    for (var i = 0; i < accessPoints.length; i++) {
        addMarkerWithInfo(accessPointsOptimal[i], i *1,APOinfo[i],imageAPOpt,markersAPO);
    }

}


/* Route from GPS Points */

var gpsPoints = [];
var route;

function addRoute(coords) {

    /* Create points of route */
    for (var i = 0 ; i < coords.length; i++) {
        gpsPoints.push(new google.maps.LatLng(coords[i][0],coords[i][1]));
    }

    /* Create route */
    route = new google.maps.Polyline({
        path: gpsPoints,
        geodesic: true,
        strokeColor: '#9900CC',
        strokeOpacity: 0.7,
        strokeWeight: 2
    });
    route.setMap(map);
    //alert(gpsPoints.length + " GPS points found");
}



/* Base Stations */

var baseStations = [];
var markersBS = [];
var BSinfo = [];
var imageBS = {
    url: 'http://wacxtv.com/portals/18/Images/antenna_icon.png'
};

function addBaseStations(geojson) {

    for (var i=0; i< geojson.features.length; i++){
        //coordinates of each base station
        var coordinates = geojson.features[i].geometry.coordinates;
        baseStations.push(new google.maps.LatLng(coordinates[0],coordinates[1]));
        var operator = geojson.features[i].properties.operator;
        var mcc = geojson.features[i].properties.mcc;
        var mnc = geojson.features[i].properties.mnc;
        var lac = geojson.features[i].properties.lac;
        var cid = geojson.features[i].properties.cid;

        BSinfo[i] = "<div> <b>Operator: </b>" + operator+ "<br/>"
                        + "<b>Mcc: </b>"+ mcc + "<br/>"
                        + "<b>Mnc: </b>"+ mnc + "<br/>"
                        + "<b>Lac: </b>"+ lac + "<br/>"
                        + "<b>Cid: </b>"+ cid + "<br/>"
                        + "</div>";
    }

    /*Create and drop */
    for (i = 0; i < baseStations.length; i++) {
        addMarkerWithInfo(baseStations[i], i * 300,BSinfo[i],imageBS,markersBS);
    }
    //alert(baseStations.length+" BS found");
}


/* Stay Points */

var stayPoints = [];
var markersSP = [];
var imageSP = {
    url: 'http://maps.google.com/mapfiles/kml/paddle/purple-stars.png'
};

function addStayPoints(coords){
    for (var i = 0 ; i < coords.length; i++) {
        // alert(coords[i]);
        stayPoints.push(new google.maps.LatLng(coords[i][0],coords[i][1]));
    }
    for (i = 0; i < stayPoints.length; i++) {
        addMarker(stayPoints[i], i * 100,imageSP,markersSP);
    }

}

/* Points of Interest */

var pointsOfInterest = []; //List of polygons
var regionCoords = [];    //List of a region's coordintates
var markersPOI = [];
var cendroids = [];
var imagePOI = {
    url: 'http://maps.google.com/mapfiles/kml/paddle/pink-stars.png'
};

function addPointsOf(geojson){

    for (var i=0; i< geojson.features.length; i++){

        //coordinates of each polygon(point of interest)
        var coordinates = geojson.features[i].geometry.coordinates;
        cendroids.push(new google.maps.LatLng(coordinates[0][0],coordinates[0][1]));
        for (var j = 0; j< coordinates.length ; j++) {
            regionCoords.push(new google.maps.LatLng(coordinates[j][0],coordinates[j][1]));
        }
        var polygon = new google.maps.Polygon({
            paths: regionCoords,
            strokeColor: '#FF0000',
            strokeOpacity: 0.8,
            strokeWeight: 2,
            fillColor: '#FF0000',
            fillOpacity: 0.35
        });
        pointsOfInterest.push(polygon);
    }

    //alert(pointsOfInterest.length + " points of interest found!");

    /* Add Points of interest as marker of the cendroid point*/
    for (i = 0; i < cendroids.length; i++) {
        addMarker(cendroids[i], i * 100,imagePOI,markersPOI);
    }

    /* Add Points of interest as triangle of (cendroid,MaxPoint,MinPoint) */
//        for (i = 0; i < pointsOfInterest.length; i++) {
//          pointsOfInterest[i].setMap(map);
//        }

}


function addMarkerWithInfo(position,timeout,infos,image,markers) {
    window.setTimeout(function() {
        var marker = new google.maps.Marker({
            position: position,
            map: map,
            icon: image,
            //shape:shape,
            animation: google.maps.Animation.DROP

        })
        markers.push(marker);
        google.maps.event.addListener(marker, 'click', (function(marker,infos) {
            return function() {
                infowindow.setContent(infos);
                infowindow.open(map, marker);
            }
        })(marker,infos));

    }, timeout);

}
function addMarker(position,timeout,image,markers) {
    window.setTimeout(function() {
        var marker = new google.maps.Marker({
            position: position,
            map: map,
            icon: image,
            //shape:shape,
            animation: google.maps.Animation.DROP

        })
        markers.push(marker);

    }, timeout);

}

/* Clear Map functions */
function clearMarkers() {

    for (var i = 0; i < markersAP.length; i++) {
        markersAP[i].setMap(null);
    }
    for (i = 0; i < markersBS.length; i++) {
        markersBS[i].setMap(null);
    }
    for (i = 0; i < markersSP.length; i++) {
        markersSP[i].setMap(null);
    }
    for (i = 0; i < pointsOfInterest.length; i++) {
        pointsOfInterest[i].setMap(null);
    }
    for (var i = 0; i < markersAPO.length; i++) {
        markersAPO[i].setMap(null);
    }
    for (var i = 0; i < markersPOI.length; i++) {
        markersPOI[i].setMap(null);
    }

    markersAP = [];
    markersBS =[];
    markersSP = [];
    markersAPO = [];
    markersPOI = [];

    accessPoints = [];
    baseStations =  [];
    stayPoints = [];
    pointsOfInterest = [];
    regionCoords = [];
    accessPointsOptimal = [];
    cendroids = [];

    APinfo = [];
    APOinfo = [];
    BSinfo = [];

}

function clearRoute() {

    gpsPoints = [];
    route.setMap(null);
}
