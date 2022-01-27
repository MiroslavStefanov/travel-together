function sleep (time) {
    return new Promise((resolve) => setTimeout(resolve, time));
}

const mapService = {
    accessToken: 'pk.eyJ1IjoicGFuZGljYW8iLCJhIjoiY2t5cTFiNDllMGZydTJxcHRpdmZnNGUxZSJ9.LbqfQLYr_WXAk6FRwCbH0A',
    map: null,
    markers: [],
    placesService: null,
    geocoder: null,

    initMap: function(mapElementId, center, zoom) {
        mapboxgl.accessToken = this.accessToken
        this.map = new mapboxgl.Map({
            container: mapElementId,
            style: 'mapbox://styles/mapbox/streets-v11', // style URL
            center: center,
            zoom: zoom
        });
    },

    placeMarker: function(position) {
        const el = document.createElement('div');
        el.className = 'marker';

        let marker = new mapboxgl.Marker(el);
        marker.setLngLat(position).addTo(this.map);
        this.markers.push(marker);
    },

    removeMarker: function() {
        if(this.markers.length > 0) {
            this.markers[this.markers.length - 1].remove();
            this.markers.pop();
        }
    },

    getPhotosByPlaceId: function (id, callback) {
        const backupPhotos = [
            {
                getUrl: (size) => {
                    return '/assets/not-found.png';
                }
            }
        ];

        if(id == null){
            callback(backupPhotos);
            return;
        }

        let request = {
            placeId: id,
            fields: ['photos']
        };

        if(this.placesService === null || this.placesService === undefined)
            this.placesService = new google.maps.places.PlacesService(map);

        this.placesService.getDetails(request, (res, status) => {
            if (status === google.maps.places.PlacesServiceStatus.OK) {
                callback(res.photos);
            } else {
                console.log('Places failed! Status: ' + status);
                callback(backupPhotos);
            }
        });
    },

    getPhotosByGeocode: function (lat, lng, callback, attempts) {
        let maxLoadSeconds = 20;
        let id;
        let found = false;
        if(this.geocoder === null || this.geocoder === undefined)
            this.geocoder = new google.maps.Geocoder;

        let func = this.getPhotosByPlaceId;
        let self = this.getPhotosByGeocode;

        this.geocoder.geocode({'location': {lat: lat, lng: lng}}, function(results, status) {
            if (status === 'OK') {
                for(r in results) {
                    let res = results[r];
                    for(t in res['types']){
                        let type = res['types'][t];
                        if(type === 'locality' || type.includes('administrative_area')) {
                            func(res['place_id'], callback);
                            found = true;
                            return;
                        }
                    }
                }
            } else if(status === 'OVER_QUERY_LIMIT') {
                if(attempts === undefined)
                    attempts = 0;
                if((attempts*2) >= maxLoadSeconds) {
                    console.log('Geocoder failed! Status: ' + status);
                    func(null, callback);
                    return;
                }
                sleep(2000).then(() => {
                    self(lat, lng, callback, attempts+1);
                });
                return;
            } else {
                console.log('Geocoder failed! Status: ' + status);
                func(null, callback);
                return;
            }
            if(found === false) {
                func(null, callback);
            }
        });
    }
};