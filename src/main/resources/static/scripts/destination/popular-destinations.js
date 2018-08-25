function showPreview(destinations, indx, elems, delay){
    // Fade out
    if(delay === undefined)
        delay = 0;

    elems.parent.removeClass('reveal');
    elems.parent.addClass('fade');
    let current = destinations[indx];

    // Fade in
    setTimeout(function () {
        elems.carousel.empty();

        let addCurrentCarousel = images => {
            addCarousel(elems.carousel, images, 'carousel-' + indx)
        };

        let callback = (photos) => {
            saveDestinationImages(photos, current.id, addCurrentCarousel);
        };

        if (current.imageUrls === undefined || current.imageUrls === null || current.imageUrls.length === 0) {
            mapService.getPhotosByGeocode(current.latitude, current.longitude, callback);
        } else {
            addCarousel(elems.carousel, current.imageUrls, 'carousel-' + indx);
        }

        elems.name.text(current.name);
        elems.description.text(current.description);
        let word = ' times';
        if (current.travelCount === 1)
            word = ' time';
        elems.visits.text('Visited ' + current.travelCount + word);
        elems.parent.removeClass('fade');
        elems.parent.addClass('reveal');
        elems.parent.attr('href', '/destinations/details/' + current.id);
    }, delay);
};

$(document).ready(() => {
    $.ajax({
        method: 'GET',
        url: '/destinations/popular',
        success: data => {
            let size = data.length;
            let indx = 0;
            let dest = $('#dest');
            let carousel = dest.find('#dest-img');
            let name = dest.find('#dest-name');
            let description = dest.find('#dest-desc');
            let visits = dest.find('#dest-visits');

            let elems = {
                parent: dest.find('#dest-body'),
                carousel: carousel,
                name: name,
                description: description,
                visits: visits
            };

            dest.find('#dest-left').click(() => {
                indx--;
                indx = (indx+size)%size;
                showPreview(data, indx, elems, 500);
            });

            dest.find('#dest-right').click(() => {
                indx++;
                indx = indx%size;
                showPreview(data, indx, elems, 500);
            });

            showPreview(data, indx, elems);
        }
    })
});