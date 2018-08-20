function initMap(mapElementId, center, zoom) {
    let map = new google.maps.Map(document.getElementById(mapElementId), {
        center: center,
        zoom: zoom
    });

    return map;
}

function placeMarker(position, map) {
    let icon = {
        url: '/assets/map-marker.png',
        scaledSize: new google.maps.Size(50, 50)
    };

    let marker = new google.maps.Marker({
        position: position,
        map: map,
        animation: google.maps.Animation.BOUNCE,
        icon: icon
    });

    return marker;
}