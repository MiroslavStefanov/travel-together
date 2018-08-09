var pageService = {
    pageButtonCount: 1,
    page: 0,
    size: 10,
    currentButtonClass: 'btn-primary',
    pageHandler: () => {},

    setSize(size) {
        this.size = size;
        this.processButtons();
    },

    setCurrentButtonClass(className) {
        this.currentButtonClass = className;
    },

    setPageHandler(pageHandler) {
        this.pageHandler = pageHandler;
    },

    initButtons(button) {
        button.text('1');
        let lastButton = button;
        let newButton;
        for (let i = 2; i <= this.pageButtonCount && i <= this.size; i++) {
            newButton = lastButton.clone();
            newButton.text(i.toString());
            lastButton.after(newButton);
            lastButton = newButton;
        }
    },

    updateButtons(buttons){
        let button;
        let stride = this.pageButtonCount;
        let page = this.page;
        let className = this.currentButtonClass;
        let update = 0;

        button = buttons.first();
        if(this.page < parseInt(button.text())-1)
            update = -1;

        button = buttons.last();
        if(this.page > parseInt(button.text())-1)
            update = 1;

        buttons.each(function(){
            let num = parseInt($(this).text());
            if(num === page+1){
                $(this).addClass(className);
            } else {
                $(this).removeClass(className);
            }

            num += update*stride;
            $(this).text(num.toString());
        });
    },

    processButtons() {
        let bar = $('#pagination-bar');
        let buttons = bar.find('.btn-page');
        let button = buttons.last();
        let elem;

        if(button.text() === '') {
            this.initButtons(button);
            buttons = bar.find('.btn-page');
        }
        this.updateButtons(buttons);

        bar.find('#prev').attr('disabled', this.page <= 0);
        bar.find('#next').attr('disabled', this.page >= this.size-1);

        button = buttons.first();
        elem = bar.find('#left-ellipsis');
        /*if(parseInt(button.text()) <= 1)
            elem.hide();
        else
            elem.show();*/
        elem.attr('hidden', parseInt(button.text()) <= 1);

        button = buttons.last();
        elem = bar.find('#right-ellipsis');
        /*if(parseInt(button.text()) >= this.size)
            elem.hide();
        else
            elem.show();*/
        elem.attr('hidden', parseInt(button.text()) >= this.size);
    },

    handlePage() {
        this.processButtons();
        this.pageHandler(this.page);
    },

    prev() {
        this.page--;
        this.handlePage();
    },

    next() {
        this.page++;
        this.handlePage();
    },

    leftEllipsis() {

    },

    handleEllipsis(id) {
        let bar = $('#pagination-bar');
        if(id === 'left-ellipsis')
            this.page = parseInt(bar.find('.btn-page').first().text())-2;
        else if(id === 'right-ellipsis')
            this.page = parseInt(bar.find('.btn-page').last().text());
    },

    selectPage(elem) {
        if(elem.innerHTML === '...'){
            this.handleEllipsis(elem.id);
        } else {
            this.page = parseInt(parseInt(elem.innerHTML) - 1);
        }
        this.handlePage();
    }
};

$(document).ready(() => pageService.handlePage());