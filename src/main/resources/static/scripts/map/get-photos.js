function getPhotosById(id, callback) {
    if(id == null){
        callback(backupPhotos);
        return;
    }

    let map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 0, lng: 0},
        zoom: 15
    });

    let request = {
        placeId: id,
        fields: ['photos']
    };

    let service = new google.maps.places.PlacesService(map);
    service.getDetails(request, (res, status) => {
        if (status === google.maps.places.PlacesServiceStatus.OK) {
            callback(res.photos);
        } else {
            console.log('Places failed! Status: ' + status);
            callback(backupPhotos);
        }
    });
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

function getPhotosByGeocode(lat, lng, callback, attempts) {
    let geocoder = new google.maps.Geocoder;
    let id;
    let found = false;
    geocoder.geocode({'location': {lat: lat, lng: lng}}, function(results, status) {
        if (status === 'OK') {
            for(r in results) {
                let res = results[r];
                for(t in res['types']){
                    let type = res['types'][t];
                    if(type === 'locality' || type.includes('administrative_area')) {
                        getPhotosById(res['place_id'], callback);
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
                getPhotosById(null, callback);
                return;
            }
            sleep(2000).then(() => {
                getPhotosByGeocode(lat, lng, callback, attempts+1);
            });
            return;
        } else {
            console.log('Geocoder failed! Status: ' + status);
            getPhotosById(null, callback);
            return;
        }
        if(found === false) {
            getPhotosById(null, callback);
        }
    });
}
