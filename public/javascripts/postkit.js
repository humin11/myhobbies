!function ($) {
    var Postkit = function (element, options) {
        this.options = options;
        this.$element = $(element);
        this.postWidth = 450;
    }

    Postkit.prototype = {

        constructor: Postkit,

        initialize: function(){
            this.$element.addClass('modal');
            var divBody = $('<div class="modal-body"></div>');
            var postContent = $('<div class="post-content"></div>');
            postContent.attr('contenteditable',true);
            var closeBtn = $('<div type="button" class="close">&times;</div>');
            var divFooter = $('<div class="modal-footer"></div>');
            closeBtn.css('position','absolute');
            closeBtn.css('left',this.postWidth-35);
            divBody.append(closeBtn);
            divBody.append(postContent);

            this.addParticipationBtn(divFooter);
            this.addShareBtn(divFooter);

            this.$element.append(divBody);
            this.$element.append(divFooter);
            this.$element.width(this.postWidth);

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
            data.initialize();
        })
    }

    $.fn.postkit.defaults = {

        show: true
    }

    $.fn.postkit.Constructor = Postkit


}(window.jQuery);
