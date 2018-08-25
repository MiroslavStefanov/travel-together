function saveDestinationImages(photos, destinationId, callback) {
    let images = [];
    if (!(photos === undefined)) {
        photos.forEach((photo) => {
            images.push(photo.getUrl({maxWidth: 1024, maxHeight: 1024}));
        });
        if(images[0] !== '/assets/not-found.png') {
            $.ajax({
                method: 'PATCH',
                url: '/destinations/' + destinationId,
                dataType: 'json',
                contentType: 'application/json',
                beforeSend: (request) => {
                    let _tc = $("meta[name='_csrf']").attr("content");
                    let _hc = $("meta[name='_csrf_header']").attr("content");
                    request.setRequestHeader(_hc, _tc);
                },
                data: JSON.stringify({
                    imageUrls: images
                })
            });
        }
        callback(images);
    } else {
        callback(['/assets/not-found.png']);
    }
}