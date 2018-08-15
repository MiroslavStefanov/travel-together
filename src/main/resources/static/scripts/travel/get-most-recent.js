$(document).ready(() => {
    $.ajax({
        type: 'GET',
        url: '/travel_api/search/findTop5ByOrderByPublishedAtDesc',
        success: (data) => {
            let section = $('#most-recent');
            let row = fillRow(data);
            section.empty();
            section.append(row);
        }
    })
});