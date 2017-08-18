package bot4jdeployment

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SlackSpecController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond SlackSpec.list(params), model:[slackSpecCount: SlackSpec.count()]
    }

    def show(SlackSpec slackSpec) {
        respond slackSpec
    }

    def create() {
        def slackSpec = new SlackSpec(params)
        def bot = Bot.get(params.long('botId'))
        slackSpec.setBot(bot)
        respond slackSpec
    }

    @Transactional
    def save(SlackSpec slackSpec) {
        if (slackSpec == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (slackSpec.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond slackSpec.errors, view:'create'
            return
        }

        slackSpec.save flush:true

        redirect controller:'bot', action:'show', params:[id:slackSpec.bot.id]

    }

    def edit(SlackSpec slackSpec) {
        respond slackSpec
    }

    @Transactional
    def update(SlackSpec slackSpec) {
        if (slackSpec == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (slackSpec.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond slackSpec.errors, view:'edit'
            return
        }

        slackSpec.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'slackSpecPayload.label', default: 'SlackSpecPayload'), slackSpec.id])
                redirect slackSpec
            }
            '*'{ respond slackSpec, [status: OK] }
        }
    }

    @Transactional
    def delete(SlackSpec slackSpec) {

        if (slackSpec == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        slackSpec.delete flush:true

        redirect controller:'bot', action:'show', params:[id:slackSpec.bot.id]

    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'slackSpecPayload.label', default: 'SlackSpecPayload'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
