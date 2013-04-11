function rightPanelHoverHandler(ele){
    ele.hover(function(){
        var viewall = $(this).find('.rightpanel-viewall');
        viewall.fadeTo(100,1);
    },function(){
        var viewall = $(this).find('.rightpanel-viewall');
        viewall.fadeTo(100,0);
    });
}

function rightPanelRowHoverHandler(ele){
    ele.hover(function(){
        $(this).find('.rightpanel-delbtn').fadeTo(100,1);
    },function(){
        $(this).find('.rightpanel-delbtn').fadeTo(100,0);
    });
}

function likeBtnClickHandler(ele){
    ele.click(function(){
        var post = $(this).parents('.post-row');
        var likenum = $(this).parent().next().find('.likenum');
        $.post('/posts/like?id='+post.attr('uid'),function(data){
            likenum.text(data);
            var likeAvatar = post.find('.like-avatar');
            if(likeAvatar.is(":hidden"))
                likeAvatar.show();
            else
                likeAvatar.hide();
            if(data > 0){
                post.find('.post-likenum').show();
            }else{
                post.find('.post-likenum').hide();
            }
        });
    });
}

function postHoverHandler(ele){
    ele.hover(function(){
        var author = $(this).find('.post-header-author');
        author.css('color','#2f63cf');
        $(this).find('.post-actions-btn').addClass('post-actions-btnHover');
    },function(){
        var author = $(this).find('.post-header-author');
        author.css('color','#000');
        $(this).find('.post-actions-btn').removeClass('post-actions-btnHover');
    });
}

function postDelBtnClickHandler(ele){
    ele.click(function(){
        var post = $(this).parents('.post').parent().parent();
        $.post('/posts/delete?id='+post.attr('uid'),function(){
            post.remove();
        });
    });
}

function postExpandBtnClickHandler(ele){
    ele.find('.post-message-expand').click(function(){
        $(this).parent().hide();
        $(this).parent().next().show();
    });
    ele.next().find('.post-message-collapse').click(function(){
        $(this).parent().hide();
        $(this).parent().prev().show();
    });
}

function commentsMoreClickHandler(ele){
    ele.click(function(){
        $(this).hide();
        $(this).next().show();
        var comments = $(this).parents('.comments');
        var id = comments.parents('.post-row').attr('uid');
        $.post('/comments/list?id='+id+'&show=all',function(data){
            comments.find('.comments-list').html(data);
            commentHoverHandler(comments.find('.comment'));
            commentDelBtnClickHandler(comments.find('.comment-delbtn'));
            commentReplyClickHandler(comments.find('.comment-reply'));
            avatarHoverHandler(comments.find('.avatar-icon'));
        });
    });
    ele.next().click(function(){
        $(this).hide();
        $(this).prev().show();
        var comments = $(this).parents('.comments');
        var id = comments.parents('.post-row').attr('uid');
        $.post('/comments/list?id='+id+'&show=3',function(data){
            comments.find('.comments-list').html(data);
            commentHoverHandler(comments.find('.comment'));
            commentDelBtnClickHandler(comments.find('.comment-delbtn'));
            commentReplyClickHandler(comments.find('.comment-reply'));
            avatarHoverHandler(comments.find('.avatar-icon'));
        });
    });
}

function commentNoInputClickHandler(ele){
    ele.click(function(){
        $(this).hide();
        var comment_input = $(this).next();
        comment_input.show();
        comment_input.find('.post-inputable').focus();
    });
}

function commentNewInputKeydownHandler(ele){
    ele.on('keydown paste',function(){
        var addbtn = $(this).parents('.comments-new-section').find('.comment-addbtn');
        setTimeout(function() {
            if(ele.text().length > 0){
                addbtn.removeClass('disabled');
            }else{
                addbtn.addClass('disabled');
            }
        },100);
    });
}

function commentAddBtnClickHandler(ele){
    ele.click(function(){
        var sendbtn = $(this);
        if(sendbtn.hasClass('disabled'))
            return false;
        var comments_list = sendbtn.parents('.comments').find('.comments-list');
        var postId = $(this).attr('post');
        var comment_input = $(this).parent().parent();
        var content = comment_input.find('.comments-new-input');
        var params = {};
        params["postId"] = postId;
        params["content"] = content.html();
        $.post('/comments/create',params,function(data){
            content.text('');
            sendbtn.next().trigger('click');
            comments_list.append(data);
            var comment_num = comments_list.prev().prev().find('.comments-num');
            comment_num.text(parseInt(comment_num.text())+1);
            if(parseInt(comment_num.text()) > 3 && comments_list.prev().is(":hidden")){
                comments_list.prev().prev().show();
                comments_list.find('.comment:first').remove();
            }
            var newComment = comments_list.find('.comment:last');
            commentHoverHandler(newComment);
            commentDelBtnClickHandler(newComment.find('.comment-delbtn'));
            commentReplyClickHandler(newComment.find('.comment-reply'));
            avatarHoverHandler(newComment.find('.avatar-icon'));
        });
    });
}

function commentCancelBtnClickHandler(ele){
    ele.click(function(){
        var comment_input = $(this).parent().parent();
        comment_input.find('.post-inputable').empty();
        comment_input.hide();
        var comment_input_fake = comment_input.prev();
        comment_input_fake.show();
    });
}

function commentHoverHandler(ele){
    ele.hover(function(){
        var delbtn = $(this).find('.comment-delbtn');
        delbtn.show();
        var reply = $(this).find('.comment-reply');
        reply.show();
    },function(){
        var delbtn = $(this).find('.comment-delbtn');
        delbtn.hide();
        var reply = $(this).find('.comment-reply');
        reply.hide();
    });
}

function commentDelBtnClickHandler(ele){
    ele.click(function(){
        var comment = $(this).parents('.comment');
        var comments = comment.parents('.comments');
        var nums = comments.find('.comments-num');
        $.post('/comments/delete?id='+comment.attr('uid'),function(){
            nums.text(parseInt(nums.text())-1);
            if(parseInt(nums.text()) == 3)
                comments.find('.comments-more').trigger('click');
            if(parseInt(nums.text()) < 4){
                comments.find('.comments-more').hide();
                comments.find('.comments-hidemore').hide();
            }
            comment.remove();
        });
    });
}

function commentReplyClickHandler(ele){
    ele.click(function(){
        var comment = $(this).parents('.comment');
        var author = comment.find('.comment-author').text();
        var comments = comment.parents('.comments');
        comments.find('.comments-new-noinput').trigger('click');
        comments.find('.comments-new-input').text('+'+author);
    });
}

function avatarHoverHandler(ele){
    var inHandler = null;
    var outHandler = null;
    ele.hover(
        function(evt){
            clearTimeout(outHandler);
            var personId = $(this).attr('uid');
            inHandler = setTimeout(function(){
                $('#avatarModal').css('left',evt.pageX+10);
                $('#avatarModal').css('top',evt.pageY);
                $.post('/people/avatar?id='+personId,function(avatarData){
                    $('#avatarModal').html(avatarData);
                    $('#avatarModal').show();
                });
            },1000);
        },function(evt){
            clearTimeout(inHandler);
            outHandler = setTimeout(function(){
                var mouseX = evt.pageX;
                var mouseY = evt.pageY;
                if(mouseX < $('#avatarModal').offset().left
                    || mouseX > $('#avatarModal').offset().left+$('#avatarModal').width()
                    || mouseY < $('#avatarModal').offset().top
                    || mouseY > $('#avatarModal').offset().top+$('#avatarModal').height()){
                    $('#avatarModal').hide();
                }
            },1000);
        }
    );
}