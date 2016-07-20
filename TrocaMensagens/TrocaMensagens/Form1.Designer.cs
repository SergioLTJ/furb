namespace TrocaMensagens
{
    partial class frmTrocaMensagens
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.grdUsuarios = new System.Windows.Forms.DataGridView();
            this.ID = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Nome = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Vitórias = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.txbMessage = new System.Windows.Forms.RichTextBox();
            this.btnSend = new System.Windows.Forms.Button();
            this.tbcChats = new System.Windows.Forms.TabControl();
            ((System.ComponentModel.ISupportInitialize)(this.grdUsuarios)).BeginInit();
            this.SuspendLayout();
            // 
            // grdUsuarios
            // 
            this.grdUsuarios.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.grdUsuarios.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.ID,
            this.Nome,
            this.Vitórias});
            this.grdUsuarios.Location = new System.Drawing.Point(565, 12);
            this.grdUsuarios.Name = "grdUsuarios";
            this.grdUsuarios.Size = new System.Drawing.Size(344, 461);
            this.grdUsuarios.TabIndex = 5;
            // 
            // ID
            // 
            this.ID.HeaderText = "ID";
            this.ID.Name = "ID";
            // 
            // Nome
            // 
            this.Nome.HeaderText = "Nome";
            this.Nome.Name = "Nome";
            // 
            // Vitórias
            // 
            this.Vitórias.HeaderText = "Vitórias";
            this.Vitórias.Name = "Vitórias";
            // 
            // txbMessage
            // 
            this.txbMessage.Location = new System.Drawing.Point(12, 419);
            this.txbMessage.Name = "txbMessage";
            this.txbMessage.Size = new System.Drawing.Size(450, 54);
            this.txbMessage.TabIndex = 6;
            this.txbMessage.Text = "";
            // 
            // btnSend
            // 
            this.btnSend.Location = new System.Drawing.Point(468, 417);
            this.btnSend.Name = "btnSend";
            this.btnSend.Size = new System.Drawing.Size(91, 56);
            this.btnSend.TabIndex = 7;
            this.btnSend.Text = "Enviar";
            this.btnSend.UseVisualStyleBackColor = true;
            this.btnSend.Click += new System.EventHandler(this.btnSend_Click);
            // 
            // tbcChats
            // 
            this.tbcChats.Location = new System.Drawing.Point(13, 13);
            this.tbcChats.Name = "tbcChats";
            this.tbcChats.SelectedIndex = 0;
            this.tbcChats.Size = new System.Drawing.Size(546, 398);
            this.tbcChats.TabIndex = 8;
            // 
            // frmTrocaMensagens
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(921, 485);
            this.Controls.Add(this.tbcChats);
            this.Controls.Add(this.btnSend);
            this.Controls.Add(this.txbMessage);
            this.Controls.Add(this.grdUsuarios);
            this.Name = "frmTrocaMensagens";
            this.Text = "Troca de mensagens";
            ((System.ComponentModel.ISupportInitialize)(this.grdUsuarios)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion
        private System.Windows.Forms.DataGridView grdUsuarios;
        private System.Windows.Forms.DataGridViewTextBoxColumn ID;
        private System.Windows.Forms.DataGridViewTextBoxColumn Nome;
        private System.Windows.Forms.DataGridViewTextBoxColumn Vitórias;
        private System.Windows.Forms.RichTextBox txbMessage;
        private System.Windows.Forms.Button btnSend;
        private System.Windows.Forms.TabControl tbcChats;
    }
}

