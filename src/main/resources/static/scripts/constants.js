const travelPageHandler = {
    baseUrl: '/travel_api/search/findAllActiveBySearchParameters?projection=travelProjection&sort=publishedAt,desc',
    arguments: {
        page: 0,
        size: 10,
        sDest: null,
        eDest: null,
        fDate: null,
        tDate: null
    },

    buildUrl: function() {
        let url = this.baseUrl;
        Object.entries(this.arguments).forEach(([key, value]) => {
            if(value !== null && value !== undefined && value !== '') {
                url += '&' + key + '=' + value;
            }
        });
        return url;
    },

    handlePage: function(page) {
        this.arguments.page = page;
        let url = this.buildUrl();
        $.ajax({
            type: 'GET',
            url: url,
            success: (data) => {
                pageService.setSize(data.page.totalPages);
                let section = $('#all-travels');
                let row = fillRow(data);
                section.empty();
                section.append(row);
            }
        })
    }
};