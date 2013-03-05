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
                this.autoHide();
            }
            var divBody = $('<div class=""></div>');
            var postMessage = $('<div class="postkit-message"></div>');
            postMessage.attr('contenteditable',true);
            postMessage.attr('placeholder','Share Something...');
            var closeBtn = $('<div type="button" class="close">&times;</div>');
            closeBtn.css('position','absolute');
            closeBtn.css('left',this.postWidth-15);
            var $el = this.$element;
            closeBtn.click(function(){
                postMessage.empty();
                $el.hide();
            });
            divBody.append(closeBtn);
            divBody.append(postMessage);

            var tools = $('<div class="postkit-toolsbody"></div>');
            this.addToolsBtn(tools);

            var particContainer = $('<div class="postkit-particbody"></div>');
            this.addParticipationBtn(particContainer);

            var divFooter = $('<div class="postkit-footer"></div>');
            var shareBtn = this.addShareBtn(divFooter);
            postMessage.keydown(function(){
                if(postMessage.text().length > 0){
                    shareBtn.removeClass('disabled');
                }else{
                    shareBtn.addClass('disabled');
                }
            });
            if(this.showtype == 'popup'){
                var kitContent = $(this.$element.find('.postkit-content')[0]);
                kitContent.append(divBody);
                kitContent.append(tools);
                kitContent.append(particContainer);
                kitContent.append(divFooter);
            }else{
                this.$element.append(divBody);
                this.$element.append(tools);
                this.$element.append(particContainer);
                this.$element.append(divFooter);
            }
            this.$element.width(this.postWidth);
        },

        autoHide: function(){
            var $ele = this.$element;
            this.$element.mouseenter(function(){
                $ele.mouseIn = true;
            });
            this.$element.mouseleave(function(){
                $ele.mouseIn = false;
            });
            $(document.body).click(function(evt){
                if(!$ele.mouseIn){
                    $ele.hide();
                }
            });
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
            var $el = this.$element;
            shareBtn.click(function(){
                if(!shareBtn.hasClass('disabled')){
                    var postContent = $($el.find('.postkit-content')[0]);
                    var content = postContent.text();
                    var params = {};
                    params["content"] = content;
                    $.post('/posts/create?'+$.param(params),function(){
                        postContent.empty();
                        $el.hide();
                    });
                }
            });
            shareBtn.addClass('disabled');
            footContainer.append(shareBtn);
            return shareBtn;
        },

        addToolsBtn: function(toolsContainer){
            var picBtn = $('<span class="postkit-tool-photo"></span>');
            toolsContainer.append(picBtn);
        },

        addParticipationBtn: function(particContainer){
            var particBtn = $('<div class="postkit-partic-content"></div>');
            particBtn.attr('contenteditable',true);
            particBtn.click(function(){

            });
            particContainer.append(particBtn);
            var addBtn = $('<div class="postkit-partic-addicon"></div>');
            particContainer.append(addBtn);
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
