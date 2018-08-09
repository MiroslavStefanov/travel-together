const backupPhotos = [
    {
        getUrl: (size) => {
            return '/assets/not-found.png';
        }
    }
];

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
            callback(backupPhotos);
        }
    });
}

function getPhotosByGeocode(lat, lng, callback) {
    let geocoder = new google.maps.Geocoder;
    geocoder.geocode({'location': {lat: lat, lng: lng}}, function(results, status) {
        if (status === 'OK') {
            for(r in results) {
                let res = results[r];
                for(t in res['types']){
                    let type = res['types'][t];
                    if(type === 'locality' || type.includes('administrative_area')) {
                        getPhotosById(res['place_id'], callback);
                    }
                }
            }
        } else {
            getPhotosById(null, callback);
        }
    });
}
