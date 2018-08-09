$(document).ready(() => {
    $.ajax({
        type: 'GET',
        url: '/travel_api/search/findTop5ByOrderByPublishedAtDesc',
        success: (data) => {
            let tempate = $('.travel-link').last().clone();
            let section = $('#most-recent');
            section.empty();
            data._embedded.travels.forEach(t => {
                let to = t.toDestination;
                let from = t.fromDestination;
                let publisher = t.publisher;
                let current = tempate.clone();
                current.find('.travel-text').text(to.name);
                current.find('.travel-desc').text(t.shortDescription);
                current.find('.travel-publisher').text('by '+publisher);
                current.attr('href', '/travels/' + t.id);
                getPhotosByGeocode(to.latitude, to.longitude, (photos) => {
                    if(!(photos === undefined)){
                        let img = current.find('.travel-img');
                        photos = photos.sort((x, y) => x.html_attributions[0] < y.html_attributions[0]);
                        //console.log(photos[0].getUrl({maxWidth: 512, maxHeight: 512}));
                        img.attr('src', photos[0].getUrl({maxWidth: 512, maxHeight: 512}));
                        img.attr('width', 512);
                        img.attr('height', 512);
                    }
                });
                section.append(current);
            })
        }
    })
});