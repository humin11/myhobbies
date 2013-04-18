
class checkListner
  constructor: (@field) ->

  change: ->
    $("#is"+@field).change (e) =>
      if $("#is"+@field).prop("checked")
        $("#"+@field).val("")
        $("#"+@field).attr("readonly", false)
      else
        $("#"+@field).val("\u514d\u8d39")
        $("#"+@field).attr("readonly","readonly")

class priceChange extends checkListner


class parkChange extends checkListner  

$ ->
	$("#businesshours").timeRangePicker({
                required: true,
                minutesStep: 10,
                include24: true
            })
  park = new priceChange "park"
  price = new parkChange "price"
  price.change()
  park.change()
	$("input,select,textarea").not("[type=submit]").jqBootstrapValidation()
	CKEDITOR.replace( 'description', {
    extraPlugins: 'autogrow',
    removePlugins: 'resize,elementspath',
    forcePasteAsPlainText : true,
    toolbar: [
      { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Strike', '-', 'RemoveFormat' ] },
      { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align' ], items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote' ] },
      { name: 'styles', items: [ 'Styles', 'Format' ] }
    ]
  });
