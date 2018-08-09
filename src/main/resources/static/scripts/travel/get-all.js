const pageSize = 1;
function loadTravels(page) {
    $.ajax({
        type: 'GET',
        url: '/travel_api?projection=travelProjection&sort=publishedAt,desc&page=' + page + '&size=' + pageSize,
        success: (data) => {
            let tempate = $('.template').last().clone();
            let section = $('#table-content');
            section.empty();
            pageService.setSize(data.page.totalPages);
            data._embedded.travels.forEach(t => {
                let current = tempate.clone();
                let date;
                current.find('.travel-link').attr('href', '/travels/'+t.id);
                current.find('.travel-from').text(t.fromDestination.name);
                current.find('.travel-to').text(t.toDestination.name);
                if(t.departureTime != null) {
                    date = new Date(t.departureTime);
                    current.find('.travel-departure').text(date.toLocaleString());
                }
                date = new Date(t.publishedAt);
                current.find('.travel-published').text(date.toLocaleString());
                current.find('.travel-publisher').text(t.publisher);
                section.append(current);
            })
        }
    })
}

pageService.setPageHandler(loadTravels);