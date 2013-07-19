!function ($) {
    var Popup = function (element, options) {
        this.options = options;
        this.$element = $(element);
        this.width = options["width"]|400;
        this.url = options["url"];
        this.anchor = options["anchor"];
        this.afterLoad = options["afterLoad"];
        this.ah = options["autoHide"];
        this.initialize();
    }

    Popup.prototype = {

        constructor: Popup,

        initialize: function(){
            this.$element.addClass('hide popup-wrapper');
            var arrow = $('<div class="popup-arrow-top"><div class="popup-arrow-top-inner"></div></div>');
            var content = $('<div class="popup-content"></div>');
            if(this.anchor != undefined)
                this.$element.append(arrow);
            this.$element.append(content);
            this.$element.width(this.width);
            if(this.ah == undefined || this.ah != false){
                this.autoHide();
            }else{
                this.$element.draggable({
                    handle: ".drag-header"
                });
            }
        },

        initPosition: function(){
            var startX,startY;
            if(this.anchor == undefined){
                startX = $(top).width()/2-this.$element.width()/2;
                startY = $(top).height()/2-this.$element.height()/2;
            }else{
                var anchorBtn = $('#'+this.anchor);
                startX = anchorBtn.offset().left-this.width/2;
                if(startX < 50)
                    startX = 50;
                startY = anchorBtn.offset().top+anchorBtn.height()+8;
                if(startY < 0)
                    startY = 0;
                if(startX + this.width > $(window).width()-30)
                    startX = startX - 30;
                var arrow = $(this.$element.find('.popup-arrow-top')[0]);
                arrow.css('left',anchorBtn.offset().left+anchorBtn.width()/2-startX);
            }
            this.$element.css('left',startX);
            this.$element.css('top',startY);
        },

        show: function(){
            var $ele = this.$element;
            var $this = this;
            $.post(this.url,function(data){
                $ele.find('.popup-content').html(data);
                $ele.show();
                $this.initPosition();
            });
        },

        autoHide: function(){
            var $ele = this.$element;
            this.$element.mouseenter(function(){
                $ele.mouseIn = true;
            });
            this.$element.mouseleave(function(){
                $ele.mouseIn = false;
            });
            $(document.body).click(function(){
                if(!$ele.mouseIn){
                    $ele.hide();
                }
            });
        }

    }

    /* POPUP PLUGIN DEFINITION
     * ======================= */

    $.fn.popup = function (option) {
        return this.each(function () {
            var $this = $(this)
                , data = $this.data('popup')
                , options = $.extend({}, $.fn.popup.defaults, $this.data(), typeof option == 'object' && option);
            if (!data)
                $this.data('popup', (data = new Popup(this, options)));
            if (typeof option == 'string')
                data[option]();
        })
    }

    $.fn.popup.Constructor = Popup


}(window.jQuery);
