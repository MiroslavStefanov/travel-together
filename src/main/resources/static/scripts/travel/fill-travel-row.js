function fillRow(data) {
    let row = $('.travel-row').first().clone();
    let template = $('.travel-body').first().clone();
    row.empty();
    data._embedded.travels.forEach((t, i) => {
        let to = t.toDestination;
        let from = t.fromDestination;
        let publisher = t.publisher;
        let current = template.clone();
        let currentCarouselId = 'carousel'+i.toString();
        current.find('.travel-text').text(to.name);
        current.find('.travel-start').text('From: ' +  from.name);
        current.find('.travel-desc').text(t.description);
        current.find('.travel-publisher').text('by '+publisher);
        current.find('a').attr('href', '/travels/' + t.id);

        let callback = (photos) => {
            let images = [];
            if (!(photos === undefined)) {
                photos.forEach((photo) => {
                    images.push(photo.getUrl({maxWidth: 1024, maxHeight: 256}));
                });
                if(images[0] !== '/assets/not-found.png') {
                    $.ajax({
                        method: 'PATCH',
                        url: '/destinations/' + to.id,
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
            } else {
                images.push('/assets/not-found.png');
            }
            addCarousel(current.find('.carousel-container'), images, currentCarouselId);
        };

        if (to.imageUrls === undefined || to.imageUrls === null || to.imageUrls.length === 0) {
            getPhotosByGeocode(to.latitude, to.longitude, callback);
        } else {
            addCarousel(current.find('.carousel-container'), to.imageUrls, currentCarouselId);
        }

        row.append(current);
    });
    return row;
}