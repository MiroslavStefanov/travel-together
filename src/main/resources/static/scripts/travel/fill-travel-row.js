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
        current.find('.travel-publisher').text('by '+publisher);
        current.find('a').attr('href', '/travels/' + t.id);

        let addCurrentCarousel = images => {
            addCarousel(current.find('.carousel-container'), images, currentCarouselId);
        };

        let callback = (photos) => {
            saveDestinationImages(photos, to.id, addCurrentCarousel)
        };

        if (to.imageUrls === undefined || to.imageUrls === null || to.imageUrls.length === 0) {
            mapService.getPhotosByGeocode(to.latitude, to.longitude, callback);
        } else {
            addCarousel(current.find('.carousel-container'), to.imageUrls, currentCarouselId);
        }

        row.append(current);
    });
    return row;
}