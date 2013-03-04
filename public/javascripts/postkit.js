!function ($) {
    var Postkit = function (element, options) {
        this.options = options;
        this.$element = $(element);
        this.postWidth = 400;
        this.anchor = options["anchor"];
        this.showtype = options["showtype"];
        this.initialize();
    }

    Postkit.prototype = {

        constructor: Postkit,

        initialize: function(){
            if(this.showtype == 'popup'){
                this.initPopup();
            }
            var divBody = $('<div class=""></div>');
            var postContent = $('<div class="post-content"></div>');
            postContent.attr('contenteditable',true);
            var closeBtn = $('<div type="button" class="close">&times;</div>');
            var divFooter = $('<div class=""></div>');
            closeBtn.css('position','absolute');
            closeBtn.css('left',this.postWidth-15);
            var $el = this.$element;
            closeBtn.click(function(){
                postContent.empty();
                $el.hide();
            });
            divBody.append(closeBtn);
            divBody.append(postContent);

            this.addParticipationBtn(divFooter);
            this.addShareBtn(divFooter);
            if(this.showtype == 'popup'){
                var kitContent = $(this.$element.find('.postkit-content')[0]);
                kitContent.append(divBody);
                kitContent.append(divFooter);
            }else{
                this.$element.append(divBody);
                this.$element.append(divFooter);
            }
            this.$element.width(this.postWidth);
        },

        initPopup: function(){
            this.$element.addClass('hide postkit-wrapper');
            var kitArrow = $('<div class="postkit-arrow-top"><div class="postkit-arrow-top-inner"></div></div>');
            var kitContent = $('<div class="postkit-content"></div>');
            this.$element.append(kitArrow);
            this.$element.append(kitContent);
        },

        initPosition: function(){
            var anchorBtn = $('#'+this.anchor);
            var postX = anchorBtn.offset().left-this.postWidth/2;
            if(postX < 50)
                postX = 50;
            var postY = anchorBtn.offset().top+anchorBtn.height()+8;
            if(postY < 0)
                postY = 0;
            var kitArrow = $(this.$element.find('.postkit-arrow-top')[0]);
            kitArrow.css('left',anchorBtn.offset().left+anchorBtn.width()/2-postX);
            this.$element.css('left',postX);
            this.$element.css('top',postY);
        },

        show: function(){
            if(this.showtype == 'popup'){
                this.initPosition();
            }
            this.$element.show();
        },

        addShareBtn: function(footContainer){
            var shareBtn = $('<a class="btn btn-success"></a>');
            shareBtn.text('Share');
            shareBtn.click(function(){
                $.post('/posts/create',function(data){

                });
            });
            footContainer.append(shareBtn);
        },

        addParticipationBtn: function(footContainer){
            var dropDown = $('<div></div>');
            var particBtn = $('<div class="participation-content dropdown-toggle" data-toggle="dropdown"></div>');
            particBtn.attr('contenteditable',true);
            particBtn.css('cursor','pointer');

            var dropDownMenu = $('<ul class="dropdown-menu" ></ul>');
            dropDownMenu.append('<li><a tabindex="-1" href="#">Public</a></li>');
            dropDownMenu.append('<li><a tabindex="-1" href="#">Circles</a></li>');
            dropDownMenu.append('<li class="divider"></li>');
            dropDownMenu.append('<li><a tabindex="-1" href="#">Test</a></li>');

            particBtn.click(function(){
                dropDownMenu.show();
            });

            dropDown.append(particBtn);
            dropDown.append(dropDownMenu);
            //footContainer.append(dropDown);

        }

    }

    /* POSTKIT PLUGIN DEFINITION
     * ======================= */

    $.fn.postkit = function (option) {
        return this.each(function () {
            var $this = $(this)
                , data = $this.data('postkit')
                , options = $.extend({}, $.fn.postkit.defaults, $this.data(), typeof option == 'object' && option);
            if (!data)
                $this.data('postkit', (data = new Postkit(this, options)));
            if (typeof option == 'string')
                data[option]();
        })
    }

    $.fn.postkit.defaults = {
        showtype: 'popup',
        show: true
    }

    $.fn.postkit.Constructor = Postkit


}(window.jQuery);
