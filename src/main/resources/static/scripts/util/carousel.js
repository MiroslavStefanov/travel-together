function addCarousel(parent, images, currentId) {
    let currentCarousel = $('#carousel').clone();
    currentCarousel.attr('id', currentId);
    currentCarousel.find('.carousel-control-next').attr('href', '#'+currentId);
    currentCarousel.find('.carousel-control-prev').attr('href', '#'+currentId);
    let ol = currentCarousel.find('ol');
    let inner = currentCarousel.find('.carousel-inner');
    let slideTemplate = ol.find('li').clone();
    let itemTemplate = inner.find('.carousel-item').clone();

    ol.empty();
    inner.empty();

    images.forEach((imgSrc, indx) => {
        let slide = slideTemplate.clone();
        slide.attr('data-target', '#'+currentId);
        slide.attr('data-slide-to', indx.toString());
        ol.append(slide);

        let item = itemTemplate.clone();
        let img = item.find('img');
        img.attr('src', imgSrc);
        inner.append(item);

        if(indx === 0) {
            slide.addClass('active');
            item.addClass('active');
        }
    });

    parent.append(currentCarousel);
}