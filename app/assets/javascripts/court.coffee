
$ ->
	$("#businesshours").timeRangePicker({
                required: true,
                minutesStep: 10,
                include24: true
            })
	$("input,select,textarea").not("[type=submit]").jqBootstrapValidation()
