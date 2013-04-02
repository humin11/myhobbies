function commentNoInputClickHandler(ele){
    ele.click(function(){
        $(this).hide();
        var comment_input = $(this).next();
        comment_input.show();
        comment_input.find('.post-inputable').focus();
    });
}

function commentAddBtnClickHandler(ele){
    ele.click(function(){
        var sendbtn = $(this);
        var comments_list = sendbtn.parents('.comments').find('.comments-list');
        var postId = $(this).attr('post');
        var comment_input = $(this).parent().parent();
        var content = comment_input.find('.comments-new-input');
        $.post('/comments/create?postId='+postId+'&content='+content.text(),function(data){
            content.text('');
            sendbtn.next().trigger('click');
            comments_list.append(data);
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
        $.post('/comments/delete?id='+comment.attr('uid'),function(){
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
    ele.hover(
        function(evt){
            var personId = $(this).attr('uid');
            $('#avatarModal').css('left',evt.pageX);
            $('#avatarModal').css('top',evt.pageY);
            $.post('/people/avatar?id='+personId,function(avatarData){
                $('#avatarModal').html(avatarData);
                $('#avatarModal').show();
            });
        },function(evt){
            setTimeout(function(){
                var mouseX = evt.pageX;
                var mouseY = evt.pageY;
                if(mouseX < $('#avatarModal').offset().left
                    || mouseX > $('#avatarModal').offset().left+$('#avatarModal').width()
                    || mouseY < $('#avatarModal').offset().top
                    || mouseY > $('#avatarModal').offset().top+$('#avatarModal').height()){
                    $('#avatarModal').hide();
                }
            },0);
        }
    );
}