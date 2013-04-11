!function ($) {
    var Postkit = function (element, options) {
        this.options = options;
        this.$element = $(element);
        this.createPostURL = '/posts/create';
        this.createTmpFilesURL = '/posts/uploadify';
        this.deleteTmpFilesURL = '/posts/deleteTmpFiles';
        this.postWidth = 400;
        this.id = options["id"];
        this.anchor = options["anchor"];
        this.showtype = options["showtype"];
        this.afterPost = options["afterPost"];
        this.tmpFiles = [];
        this.initialize();
    }

    Postkit.prototype = {

        constructor: Postkit,

        initialize: function(){
            if(this.showtype == 'popup'){
                this.initPopup();
                this.autoHide();
            }
            var tools = $('<div class="postkit-toolsbody"></div>');
            var picContainer = this.addToolsBtn(tools);

            var particContainer = $('<div class="postkit-particbody"></div>');
            //this.addParticipationBtn(particContainer);

            var divFooter = $('<div class="postkit-footer"></div>');
            var shareBtn = this.addShareBtn(divFooter);

            var divBody = $('<div class="postkit-message-main"></div>');
            var postMessage = $('<div class="postkit-message"></div>');
            postMessage.attr('contenteditable','plaintext-only');
            postMessage.attr('placeholder','Share Something...');
            if(this.showtype == 'popup'){
                postMessage.css('max-height',200);
            }
            var closeBtn = $('<div type="button" class="close">&times;</div>');
            closeBtn.css('position','absolute');
            closeBtn.css('left',this.postWidth-15);
            var $this = this;

            closeBtn.click(function(){
                if($this.tmpFiles.length > 0){
                    var params = {};
                    params["tmpfiles"] = $this.tmpFiles;
                    $.post($this.deleteTmpFilesURL,params);
                    $this.tmpFiles = [];
                }
                postMessage.empty();
                picContainer.empty();
                $this.$element.hide();
                shareBtn.addClass('disabled');
            });
            divBody.append(closeBtn);
            divBody.append(postMessage);

            postMessage.on('keydown paste',function(){
                setTimeout(function() {
                    if(postMessage.text().length > 0){
                        shareBtn.removeClass('disabled');
                    }else{
                        shareBtn.addClass('disabled');
                    }
                },100);
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
            $(document.body).click(function(){
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
            if(postX + this.postWidth > $(window).width()-30)
                postX = postX - 30;
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
            var $this = this;
            var $url = this.createPostURL;
            shareBtn.click(function(){
                if(!shareBtn.hasClass('disabled')){
                    var postContent = $($el.find('.postkit-message')[0]);
                    var content = postContent.html();
                    var params = {};
                    params["content"] = content;
                    params["tmpfiles"] = $this.tmpFiles;
                    $.post($url,params,function(data){
                        $this.afterPost(data);
                        $this.$element.find('.picContainer').empty();
                        $this.tmpFiles = [];
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
            var picBtn = $('<input type="file" name="pic" id="'+this.id+'_uploadify"/>');
            var picProgressBar = $('<div class="progress progress-striped active hide pull-right"></div>');
            picProgressBar.css('border-radius','2px');
            picProgressBar.css('margin-top','20px');
            picProgressBar.css('margin-right','30px');
            picProgressBar.width(200);
            picProgressBar.height(18);
            var bar = $('<div class="bar" style="width: 0%;"></div>');
            picProgressBar.append(bar);
            var picContainer = $('<div class="picContainer"></div>');
            picContainer.css('text-align','center');
            picContainer.css('margin-top','20px');
            var $this = this;
            var $url = this.createTmpFilesURL;
            picBtn.ready(function(){
                picBtn.uploadify({
                    swf: '/assets/img/uploadify.swf',
                    uploader: $url,
                    buttonClass: 'postkit-tool-photo',
                    width: 18,
                    height: 18,
                    buttonText: '',
                    method: 'post',
                    auto: true,
                    fileTypeDesc : 'Image Files',
                    fileTypeExts : '*.gif; *.jpg; *.png',
                    multi: false,
                    overrideEvents : ['onSelect'],
                    onUploadStart: function(){
                        picProgressBar.show();
                    },
                    onUploadError: function(){
                        setTimeout(picProgressBar.hide(),2000);
                    },
                    onUploadProgress: function(file,bytesUploaded,bytesTotal,totalBytesUploaded,totalBytesTotal){
                        bar.css('width',bytesUploaded/bytesTotal*100+'%');
                        bar.text((bytesTotal/1024).toFixed(2)+' KB');
                    },
                    onUploadSuccess : function(file, data, response){
                        $this.tmpFiles.push(data);
                        var img = $('<img src="'+data+'" style="max-width:400px;"/>');
                        setTimeout(function(){
                            picProgressBar.hide();
                            picContainer.append(img)
                            return false;
                        },2000);
                        return false;
                    }
                });
            });
            toolsContainer.append(picBtn);
            toolsContainer.append(picProgressBar);
            toolsContainer.append(picContainer);
            return picContainer;
        },

        addParticipationBtn: function(particContainer){
            var particBtn = $('<div class="postkit-partic-content span10"></div>');
            particBtn.attr('contenteditable',true);
            particBtn.click(function(){

            });
            particContainer.append(particBtn);
            var addBtn = $('<div class="span2"><div class="postkit-partic-addicon"></div></div>');
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
